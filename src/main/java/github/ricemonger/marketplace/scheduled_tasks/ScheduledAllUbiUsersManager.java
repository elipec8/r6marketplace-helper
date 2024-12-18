package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.CentralTradeManager;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.marketplace.services.TelegramUserUbiAccountEntryService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.auth.AuthorizationDTO;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllUbiUsersManager {

    public static final int TRADE_MANAGER_FIXED_RATE_MINUTES = 1;

    private final TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;

    private final TelegramBotService telegramBotService;

    private final GraphQlClientService graphQlClientService;

    private final CommonValuesService commonValuesService;

    private final ItemService itemService;

    private final CentralTradeManager centralTradeManager;

    @Scheduled(fixedRate = TRADE_MANAGER_FIXED_RATE_MINUTES * 60 * 1000, initialDelay = 5 * 60 * 1000) // every 1m after 5m of delay
    public void fetchAllUbiUsersStatsAndManageTrades() {
        List<UbiAccountEntryWithTelegram> ubiAccountsWithTelegram = telegramUserUbiAccountEntryService.findAllForTelegram();

        List<UbiAccountStats> updatedUbiAccountsStats = new ArrayList<>();

        for (UbiAccountEntryWithTelegram ubiAccountWithTelegram : ubiAccountsWithTelegram) {
            updatedUbiAccountsStats.add(fetchAndGetUserPersonalStats(ubiAccountWithTelegram));
        }

        commonValuesService.setLastUbiUsersStatsFetchTime(LocalDateTime.now().withNano(0));

        telegramUserUbiAccountEntryService.saveAllUbiAccountStats(updatedUbiAccountsStats);

        centralTradeManager.manageAllUsersTrades();
    }

    private UbiAccountStatsWithTrades fetchAndGetUserPersonalStats(UbiAccountEntryWithTelegram ubiAccountWithTelegram) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(ubiAccountWithTelegram);
        int creditAmount = graphQlClientService.fetchCreditAmountForUser(authorizationDTO);
        List<UbiTrade> currentOrders = graphQlClientService.fetchCurrentOrdersForUser(authorizationDTO);
        List<UbiTrade> finishedOrders = graphQlClientService.fetchLastFinishedOrdersForUser(authorizationDTO);
        UserTradesLimitations userTradesLimitations = graphQlClientService.fetchTradesLimitationsForUser(authorizationDTO);
        List<ItemResaleLockWithUbiAccount> itemResaleLocks = userTradesLimitations.getItemResaleLocksWithUbiAccount();
        List<String> ownedItemsIds = graphQlClientService.fetchAllOwnedItemsIdsForUser(authorizationDTO);

        LocalDateTime lastDayPeriod = LocalDateTime.now().minusDays(1).withNano(0);

        List<UbiTrade> soldIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Sell) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> boughtIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Buy) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> currentSellTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Sell)).toList();
        List<UbiTrade> currentBuyTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Buy)).toList();

        if (ubiAccountWithTelegram.getPrivateNotificationsEnabledFlag() && ubiAccountWithTelegram.getChatId() != null) {
            notifyUser(ubiAccountWithTelegram, creditAmount, soldIn24h, boughtIn24h);
        }

        return new UbiAccountStatsWithTrades(
                ubiAccountWithTelegram.getUbiProfileId(),
                userTradesLimitations.getResolvedSellTransactionCount(),
                userTradesLimitations.getResolvedBuyTransactionCount(),
                creditAmount,
                ownedItemsIds,
                itemResaleLocks,
                currentSellTrades,
                currentBuyTrades);
    }

    private void notifyUser(UbiAccountEntryWithTelegram ubiAccountWithTelegram, int creditAmount, List<UbiTrade> soldIn24h, List<UbiTrade> boughtIn24h) {

        boolean creditsChanged = ubiAccountWithTelegram.getCreditAmount() != null && ubiAccountWithTelegram.getCreditAmount() != creditAmount;
        boolean soldIn24hChanged = soldIn24h.stream().anyMatch(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime()));
        boolean boughtIn24hChanged = boughtIn24h.stream().anyMatch(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime()));

        if (creditsChanged || soldIn24hChanged || boughtIn24hChanged) {
            StringBuilder message = new StringBuilder();

            if (creditsChanged) {
                message.append("Your credit amount has changed. Old:").append(ubiAccountWithTelegram.getCreditAmount()).append(" New : ").append(creditAmount).append("\n");
            }
            if (soldIn24hChanged) {
                message.append("You have additionally sold these items in the last 24 hours:\n");
                for (UbiTrade trade : soldIn24h.stream().filter(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime())).toList()) {
                    message.append(getFinishedTradeString(trade)).append("\n");
                }
            }
            if (boughtIn24hChanged) {
                message.append("You have additionally bought these items in the last 24 hours:\n");
                for (UbiTrade trade : boughtIn24h.stream().filter(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime())).toList()) {
                    message.append(getFinishedTradeString(trade)).append("\n");
                }
            }

            telegramBotService.sendNotificationToUser(ubiAccountWithTelegram.getChatId(), message.toString());
        }
    }

    private String getFinishedTradeString(UbiTrade trade) {
        Item item = itemService.getItemById(trade.getItemId());
        return item.getName() + " : " + item.getAssetUrl() + " for " + (trade.getSuccessPaymentPrice() - trade.getSuccessPaymentFee());
    }
}
