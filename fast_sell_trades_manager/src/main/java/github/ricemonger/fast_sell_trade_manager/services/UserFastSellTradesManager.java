package github.ricemonger.fast_sell_trade_manager.services;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellCommand;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.fast_sell_trade_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFastSellTradesManager {
    private final PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;

    private final CommonValuesService commonValuesService;

    private final PotentialTradeFactory potentialTradeFactory;

    private final TradeManagementCommandsFactory tradeManagementCommandsFactory;
    private final TradeManagementCommandsExecutor fastTradeManagementCommandExecutor;

    private final List<CompletableFuture<?>> createFastSellCommandsTasks = new CopyOnWriteArrayList<>();
    private final List<FastSellCommand> fastSellCommands = new CopyOnWriteArrayList<>();

    public void executeFastSellCommandsOrSubmitCreateCommandsTask(FastSellManagedUser managedUser, List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity, int sellLimit, int sellSlots) {
        if (fastSellCommands.isEmpty()) {
            submitCreateFastSellCommandsTask(managedUser, itemsMedianPriceAndRarity, sellLimit, sellSlots);
        } else {
            cancelAllCreateFastSellCommandsTasks();

            executeCommandsInOrder(fastSellCommands);

            fastSellCommands.clear();
        }
    }

    public void createAndExecuteCommandsToKeepOneSellSlotUnused(FastSellManagedUser managedUser, List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity, int sellLimit, int sellSlots) {
        try {
            FastUserUbiStats userStats = personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(managedUser.toAuthorizationDTO(), commonValuesService.getFastTradeOwnedItemsLimit());

            List<FastSellCommand> keepUnusedSellSlotCommands = tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(managedUser, userStats.getCurrentSellOrders(), userStats.getItemsCurrentPrices(), itemsMedianPriceAndRarity, sellLimit, sellSlots);

            executeCommandsInOrder(keepUnusedSellSlotCommands);

        } catch (Exception e) {
            log.error("Error while keeping unused sell slot for user with id: {} : {}", managedUser.getUbiProfileId(), e.getMessage());
        }
    }

    private void executeCommandsInOrder(List<FastSellCommand> commands) {
        for (FastSellCommand command : commands.stream().sorted().toList()) {
            fastTradeManagementCommandExecutor.executeCommand(command);
            log.info("Executed command: {}", command.toLogString());
        }
    }

    private void submitCreateFastSellCommandsTask(FastSellManagedUser managedUser, List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity, int sellLimit, int sellSlots) {
        CompletableFuture<?> task = CompletableFuture.supplyAsync(() -> fastSellCommands.addAll(createFastSellCommandsByCurrentStats(managedUser, itemsMedianPriceAndRarity, sellLimit, sellSlots)));
        createFastSellCommandsTasks.add(task);
    }

    private void cancelAllCreateFastSellCommandsTasks() {
        for (Future<?> future : createFastSellCommandsTasks) {
            future.cancel(true);
        }
        createFastSellCommandsTasks.clear();
    }

    private List<FastSellCommand> createFastSellCommandsByCurrentStats(FastSellManagedUser managedUser, List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity, int sellLimit, int sellSlots) {
        try {
            FastUserUbiStats userStats = personalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(managedUser.toAuthorizationDTO(), commonValuesService.getFastTradeOwnedItemsLimit());

            List<PotentialTrade> items = potentialTradeFactory.createPotentialTradesForUser(userStats.getItemsCurrentPrices(), itemsMedianPriceAndRarity, commonValuesService.getMinMedianPriceDifference(), commonValuesService.getMinMedianPriceDifferencePercentage());

            return tradeManagementCommandsFactory.createFastSellCommandsForUser(managedUser, userStats.getCurrentSellOrders(), userStats.getItemsCurrentPrices(), itemsMedianPriceAndRarity, items, sellLimit, sellSlots);
        } catch (Exception e) {
            log.error("Error while creating fast sell commands for user with id: {} : {}", managedUser.getUbiProfileId(), e.getMessage());
            return List.of();
        }
    }
}
