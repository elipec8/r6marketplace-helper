package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.TelegramUserUbiAccountEntryService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
import github.ricemonger.utils.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.UbiAccountEntryWithTelegram;
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
public class ScheduledAllUbiUsersStatsFetcher {

    private final TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;

    private final TelegramBotService telegramBotService;

    private final GraphQlClientService graphQlClientService;

    private final CommonValuesService commonValuesService;

    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 2 * 60 * 1000) // every 5m after 2m of delay
    public void fetchAllUbiUsersStats() {
        List<UbiAccountEntryWithTelegram> ubiAccountsWithTelegram = telegramUserUbiAccountEntryService.findAllForTelegram();

        List<UbiAccountStats> updatedUbiAccountsStats = new ArrayList<>();

        for (UbiAccountEntryWithTelegram ubiAccountWithTelegram : ubiAccountsWithTelegram) {
            updatedUbiAccountsStats.add(fetchAndGetUserPersonalStats(ubiAccountWithTelegram));
        }

        commonValuesService.setLastUbiUsersStatsFetchTime(LocalDateTime.now().withNano(0));

        telegramUserUbiAccountEntryService.saveAllUbiAccountStats(updatedUbiAccountsStats);
    }

    private UbiAccountStats fetchAndGetUserPersonalStats(UbiAccountEntryWithTelegram ubiAccountWithTelegram) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(ubiAccountWithTelegram);
        int creditAmount = graphQlClientService.fetchCreditAmountForUser(authorizationDTO);
        List<UbiTrade> currentOrders = graphQlClientService.fetchCurrentOrdersForUser(authorizationDTO);
        List<UbiTrade> finishedOrders = graphQlClientService.fetchFinishedOrdersForUser(authorizationDTO);
        List<ItemResaleLockWithUbiAccount> itemResaleLocks = graphQlClientService.fetchLockedItemsForUser(authorizationDTO);
        List<String> ownedItemsIds = graphQlClientService.fetchAllOwnedItemsIdsForUser(authorizationDTO);

        LocalDateTime lastDayPeriod = LocalDateTime.now().minusDays(1).withNano(0);

        List<UbiTrade> soldIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Sell) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> boughtIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Buy) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> currentBuyTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Buy)).toList();
        List<UbiTrade> currentSellTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Sell)).toList();

        if (ubiAccountWithTelegram.getPrivateNotificationsEnabledFlag() && ubiAccountWithTelegram.getChatId() != null) {
            notifyUser(ubiAccountWithTelegram, creditAmount, soldIn24h, boughtIn24h);
        }

        return new UbiAccountStats(
                ubiAccountWithTelegram.getUbiProfileId(),
                soldIn24h.size(),
                boughtIn24h.size(),
                creditAmount,
                ownedItemsIds,
                itemResaleLocks,
                currentBuyTrades,
                currentSellTrades,
                finishedOrders);
    }

    private void notifyUser(UbiAccountEntryWithTelegram ubiAccountWithTelegram, int creditAmount, List<UbiTrade> soldIn24h, List<UbiTrade> boughtIn24h) {
        boolean creditsChanged = ubiAccountWithTelegram.getCreditAmount() != creditAmount;
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
                    message.append(trade.getItemId()).append(" for ").append(trade.getSuccessPaymentPrice() - trade.getSuccessPaymentFee()).append("\n");
                }
            }
            if (boughtIn24hChanged) {
                message.append("You have additionally bought these items in the last 24 hours:\n");
                for (UbiTrade trade : boughtIn24h.stream().filter(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime())).toList()) {
                    message.append(trade.getItemId()).append(" for ").append(trade.getSuccessPaymentPrice()).append("\n");
                }
            }

            telegramBotService.sendNotificationToUser(ubiAccountWithTelegram.getChatId(), message.toString());
        }
    }
}
