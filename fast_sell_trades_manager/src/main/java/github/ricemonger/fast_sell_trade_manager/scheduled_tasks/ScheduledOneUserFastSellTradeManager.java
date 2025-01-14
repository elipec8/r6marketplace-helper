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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    private FastSellManagedUser managedUser;
    private List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = new ArrayList<>();

    @Scheduled(fixedRateString = "${app.scheduling.management.fixedRate}", initialDelayString = "${app.scheduling.management.initialDelay}")
    public void manageOneUserFastSellTrades() {
        List<SellTrade> sellTrades = personalQueryCurrentSellOrdersGraphQlClientService.fetchCurrentSellOrdersForUser(managedUser.toAuthorizationDTO());

        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<ItemCurrentPrices> itemCurrentPrices = personalQueryOwnedItemsPricesGraphQlClientService.fetchOwnedItemsCurrentPricesForUser(managedUser.toAuthorizationDTO(), 120);
        List<PotentialTrade> items = tradeManagementItemsFactory.createFilteredTradeManagementItemsForUser(itemCurrentPrices, itemsMedianPriceAndRarity, commonValuesService.getMinMedianPriceDifference(), commonValuesService.getMinMedianPriceDifferencePercentage());

        List<FastTradeManagerCommand> commands = tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(managedUser, sellTrades,
                items, itemsMedianPriceAndRarity, configTrades);

        for (FastTradeManagerCommand command : commands) {
            fastTradeManagementCommandExecutor.executeCommand(command);
        }
    }

    @Scheduled(fixedRateString = "${app.scheduling.median_prices_fetch.fixedRate}", initialDelayString = "${app.scheduling.median_prices_fetch.initialDelay}")
    public void fetchItemMedianPriceFromDb() {
        itemsMedianPriceAndRarity = ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity(managedUser.getUbiProfileId());
    }

    @Scheduled(fixedRateString = "${app.scheduling.user_fetch.fixedRate}", initialDelayString = "${app.scheduling.user_fetch.initialDelay}")
    public void fetchManagedUserAuthorizationFromDb() {
        managedUser = ubiAccountEntryService.getFastSellManagedUserById(commonValuesService.getFastSellManagedUserId(), commonValuesService.getFastSellManagedUserEmail());
    }
}
