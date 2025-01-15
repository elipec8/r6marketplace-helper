package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastTradeManagerCommand;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.fast_sell_trade_manager.services.TradeManagementCommandsExecutor;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountEntryService;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementItemsFactory;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.PersonalQueryCurrentSellOrdersGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.PersonalQueryOwnedItemsPricesGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.DTOs.personal.SellTrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.FieldAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledOneUserFastSellTradeManager {
    private final CommonValuesService commonValuesService;
    private final UbiAccountEntryService ubiAccountEntryService;
    private final TradeManagementCommandsFactory tradeManagementCommandsFactory;
    private final TradeManagementCommandsExecutor fastTradeManagementCommandExecutor;
    private final PersonalQueryOwnedItemsPricesGraphQlClientService personalQueryOwnedItemsPricesGraphQlClientService;
    private final PersonalQueryCurrentSellOrdersGraphQlClientService personalQueryCurrentSellOrdersGraphQlClientService;
    private final TradeManagementItemsFactory tradeManagementItemsFactory;

    private int sellSlots;
    private int sellLimit;
    private FastSellManagedUser managedUser;
    private List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = new ArrayList<>();

    // for CompletableFuture throws java.lang.IllegalStateException: Failed to find document, name='personal_query_owned_items_prices', under location
    //  (s)=[class path resource [graphql-documents/]] in docker container, no exception occurs in local environment or if .document() used instead
    //  of .documentName()
    @Scheduled(fixedRateString = "${app.scheduling.management.fixedRate}", initialDelayString = "${app.scheduling.management.initialDelay}")
    public void manageOneUserFastSellTrades() {
        CompletableFuture<List<ItemCurrentPrices>> itemsCurrentPricesFuture = CompletableFuture.supplyAsync(() -> personalQueryOwnedItemsPricesGraphQlClientService.fetchOwnedItemsCurrentPricesForUser(managedUser.toAuthorizationDTO(), commonValuesService.getFastTradeOwnedItemsLimit()));

        CompletableFuture<List<SellTrade>> sellTradesFuture = CompletableFuture.supplyAsync(() -> personalQueryCurrentSellOrdersGraphQlClientService.fetchCurrentSellOrdersForUser(managedUser.toAuthorizationDTO()));

        try {
            List<PotentialTrade> items = tradeManagementItemsFactory.createFilteredTradeManagementItemsForUser(itemsCurrentPricesFuture.get(), itemsMedianPriceAndRarity, commonValuesService.getMinMedianPriceDifference(), commonValuesService.getMinMedianPriceDifferencePercentage());

            List<FastTradeManagerCommand> commands = tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(managedUser, sellTradesFuture.get(), items, itemsMedianPriceAndRarity, sellLimit, sellSlots);

            for (FastTradeManagerCommand command : commands.stream().sorted().toList()) {
                fastTradeManagementCommandExecutor.executeCommand(command);
                log.info("Executed command: {}", command);
            }
        }
        catch (Exception e) {
            log.error("Error while managing fast sell trades for user with id: " + managedUser.getUbiProfileId(), e);
        }
    }

    @Scheduled(fixedRateString = "${app.scheduling.median_prices_fetch.fixedRate}", initialDelayString = "${app.scheduling.median_prices_fetch.initialDelay}")
    public void fetchItemMedianPriceFromDb() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();
        sellSlots = configTrades.getSellSlots();
        sellLimit = configTrades.getSellLimit();
        itemsMedianPriceAndRarity = ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity(managedUser.getUbiProfileId());
    }

    @Scheduled(fixedRateString = "${app.scheduling.user_fetch.fixedRate}", initialDelayString = "${app.scheduling.user_fetch.initialDelay}")
    public void fetchManagedUserAuthorizationFromDb() {
        managedUser = ubiAccountEntryService.getFastSellManagedUserById(commonValuesService.getFastSellManagedUserId(), commonValuesService.getFastSellManagedUserEmail());
    }
}
