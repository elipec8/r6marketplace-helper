package github.ricemonger.ubi_users_stats_fetcher.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.personal_query_credits_amount.PersonalQueryCreditAmountGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_current_orders.PersonalQueryCurrentOrdersGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_finished_orders.PersonalQueryFinishedOrdersGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_locked_items.PersonalQueryLockedItemsGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlClientService;
import github.ricemonger.ubi_users_stats_fetcher.services.CommonValuesService;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserUbiAccount;
import github.ricemonger.ubi_users_stats_fetcher.services.TelegramBotService;
import github.ricemonger.ubi_users_stats_fetcher.services.UbiAccountService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
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

    private final UbiAccountService ubiAccountService;

    private final TelegramBotService telegramBotService;

    private final PersonalQueryCreditAmountGraphQlClientService personalQueryCreditAmountGraphQlClientService;

    private final PersonalQueryCurrentOrdersGraphQlClientService personalQueryCurrentOrdersGraphQlClientService;

    private final PersonalQueryFinishedOrdersGraphQlClientService personalQueryFinishedOrdersGraphQlClientService;

    private final PersonalQueryOwnedItemsGraphQlClientService personalQueryOwnedItemsGraphQlClientService;

    private final PersonalQueryLockedItemsGraphQlClientService personalQueryLockedItemsGraphQlClientService;

    private final CommonValuesService commonValuesService;

    @Scheduled(fixedRate = 1 * 60 * 1000, initialDelay = 5 * 60 * 1000) // every 1m after 5m of delay
    public void fetchAllAuthorizedUbiUsersStats() {
        List<UserUbiAccount> userUbiAccounts = ubiAccountService.findAllUsersUbiAccountEntries();

        List<UbiAccountStats> updatedUbiAccountsStats = new ArrayList<>();

        for (UserUbiAccount userUbiAccount : userUbiAccounts) {
            updatedUbiAccountsStats.add(fetchAndGetUserPersonalStats(userUbiAccount));
        }

        commonValuesService.setLastUbiUsersStatsFetchTime(LocalDateTime.now().withNano(0));

        ubiAccountService.saveAllUbiAccountStats(updatedUbiAccountsStats);
    }

    private UbiAccountStats fetchAndGetUserPersonalStats(UserUbiAccount userUbiAccount) {
        AuthorizationDTO authorizationDTO = userUbiAccount.toAuthorizationDTO();
        int creditAmount = personalQueryCreditAmountGraphQlClientService.fetchCreditAmountForUser(authorizationDTO);
        List<UbiTrade> currentOrders = personalQueryCurrentOrdersGraphQlClientService.fetchCurrentOrdersForUser(authorizationDTO);
        List<UbiTrade> finishedOrders =personalQueryFinishedOrdersGraphQlClientService.fetchLastFinishedOrdersForUser(authorizationDTO);
        UserTradesLimitations userTradesLimitations = personalQueryLockedItemsGraphQlClientService.fetchTradesLimitationsForUser(authorizationDTO);
        List<ItemResaleLock> itemResaleLocks = userTradesLimitations.getResaleLocks();
        List<String> ownedItemsIds = personalQueryOwnedItemsGraphQlClientService.fetchAllOwnedItemsIdsForUser(authorizationDTO);

        LocalDateTime lastDayPeriod = LocalDateTime.now().minusDays(1).withNano(0);

        List<UbiTrade> soldIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Sell) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> boughtIn24h = finishedOrders.stream()
                .filter(order -> order.getCategory().equals(TradeCategory.Buy) && order.getLastModifiedAt().isAfter(lastDayPeriod)).toList();

        List<UbiTrade> currentSellTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Sell)).toList();
        List<UbiTrade> currentBuyTrades = currentOrders.stream().filter(order -> order.getCategory().equals(TradeCategory.Buy)).toList();

            notifyUser(userUbiAccount, creditAmount, soldIn24h, boughtIn24h);

        return new UbiAccountStats(
                userUbiAccount.getProfileId(),
                userTradesLimitations.getResolvedSellTransactionCount(),
                userTradesLimitations.getResolvedBuyTransactionCount(),
                creditAmount,
                ownedItemsIds,
                itemResaleLocks,
                currentSellTrades,
                currentBuyTrades);
    }

    private void notifyUser(UserUbiAccount userUbiAccount, int creditAmount, List<UbiTrade> soldIn24h, List<UbiTrade> boughtIn24h) {

        boolean creditsChanged = userUbiAccount.getCreditAmount() != null && userUbiAccount.getCreditAmount() != creditAmount;
        boolean soldIn24hChanged = soldIn24h.stream().anyMatch(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime()));
        boolean boughtIn24hChanged = boughtIn24h.stream().anyMatch(trade -> trade.getLastModifiedAt().isAfter(commonValuesService.getLastUbiUsersStatsFetchTime()));

        if (creditsChanged || soldIn24hChanged || boughtIn24hChanged) {
            StringBuilder message = new StringBuilder();

            if (creditsChanged) {
                message.append("Your credit amount has changed. Old:").append(userUbiAccount.getCreditAmount()).append(" New : ").append(creditAmount).append("\n");
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

            telegramBotService.sendPrivateNotification(userUbiAccount.getUserId(), message.toString());
        }
    }

    private String getFinishedTradeString(UbiTrade trade) {
        Item item = trade.getItem();
        Integer price;

        if (trade.getSuccessPaymentPrice() == null || trade.getSuccessPaymentFee() == null) {
            price = trade.getSuccessPaymentPrice();
        } else {
            price = trade.getSuccessPaymentPrice() - trade.getSuccessPaymentFee();
        }

        return item.getName() + " : " + item.getAssetUrl() + " for " + price;
    }
}
