package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastTradeManagerCommand;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.fast_sell_trade_manager.services.TradeManagementCommandsExecutor;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountEntryService;
import github.ricemonger.fast_sell_trade_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
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
    private final PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
    private final PotentialTradeFactory potentialTradeFactory;

    private int sellSlots;
    private int sellLimit;
    private FastSellManagedUser managedUser;
    private List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = new ArrayList<>();

    @Scheduled(fixedRateString = "${app.scheduling.management.fixedRate}", initialDelayString = "${app.scheduling.management.initialDelay}")
    public void manageOneUserFastSellTrades() {
        try {
            FastUserUbiStats userStats = personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(managedUser.toAuthorizationDTO(), commonValuesService.getFastTradeOwnedItemsLimit());

            List<PotentialTrade> items = potentialTradeFactory.createPotentialTradesForUser(userStats.getItemsCurrentPrices(),
                    itemsMedianPriceAndRarity, commonValuesService.getMinMedianPriceDifference(), commonValuesService.getMinMedianPriceDifferencePercentage());

            List<FastTradeManagerCommand> commands = tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(managedUser, userStats.getCurrentSellOrders(), userStats.getItemsCurrentPrices(), itemsMedianPriceAndRarity, items, sellLimit, sellSlots);

            for (FastTradeManagerCommand command : commands.stream().sorted().toList()) {
                fastTradeManagementCommandExecutor.executeCommand(command);
                log.info("Executed command: {}", command);
            }
        } catch (Exception e) {
            log.error("Error while managing fast sell trades for user with id: {} : {}", managedUser.getUbiProfileId(), e.getMessage());
        }
    }

    @Scheduled(fixedRateString = "${app.scheduling.keep_unused_slot.fixedRate}", initialDelayString = "${app.scheduling.keep_unused_slot.initialDelay}")
    public void keepUnusedOneSellSlotForManagedUser() {
        try {
            FastUserUbiStats userStats = personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(managedUser.toAuthorizationDTO(), commonValuesService.getFastTradeOwnedItemsLimit());
            List<FastTradeManagerCommand> commands = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(managedUser,
                    userStats.getCurrentSellOrders(), userStats.getItemsCurrentPrices(), itemsMedianPriceAndRarity, sellLimit, sellSlots);

            for (FastTradeManagerCommand command : commands.stream().sorted().toList()) {
                fastTradeManagementCommandExecutor.executeCommand(command);
                log.info("Executed command: {}", command);
            }
        } catch (Exception e) {
            log.error("Error while keeping unused sell slot for user with id: {} : {}", managedUser.getUbiProfileId(), e.getMessage());
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
