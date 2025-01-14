package github.ricemonger.trades_manager.scheduled_tasks;

import github.ricemonger.trades_manager.services.CommonValuesService;
import github.ricemonger.trades_manager.services.DTOs.*;
import github.ricemonger.trades_manager.services.ItemService;
import github.ricemonger.trades_manager.services.TradeManagementCommandsExecutor;
import github.ricemonger.trades_manager.services.UserService;
import github.ricemonger.trades_manager.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.trades_manager.services.factories.PersonalItemFactory;
import github.ricemonger.trades_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllUbiUsersManager {
    private final CommonValuesService commonValuesService;
    private final ItemService itemService;
    private final UserService userService;
    private final PersonalItemFactory personalItemFactory;
    private final PotentialTradeFactory potentialTradeFactory;
    private final CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;
    private final TradeManagementCommandsExecutor tradeManagementCommandsExecutor;

    @Scheduled(fixedRateString = "${app.scheduling.fixedRate}", initialDelayString = "${app.scheduling.initialDelay}")
    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<ManageableUser> manageableUsers = userService.getAllManageableUsers();

        for (ManageableUser manageableUser : manageableUsers) {
            try {
                manageUserTrades(manageableUser, configTrades, items);
            } catch (Exception e) {
                log.error("Exception while managing trades for user: {}", manageableUser, e);
            }
        }
    }

    public void manageUserTrades(ManageableUser manageableUser, ConfigTrades configTrades, Collection<Item> existingItems) {

        List<Trade> currentSellTrades = manageableUser.getCurrentSellTrades();
        List<Trade> currentBuyTrades = manageableUser.getCurrentBuyTrades();

        Set<PersonalItem> personalItems = personalItemFactory.getPersonalItemsForUser(
                manageableUser.getTradeByFiltersManagers(),
                manageableUser.getTradeByItemIdManagers(),
                currentSellTrades,
                currentBuyTrades,
                manageableUser.getOwnedItemsIds(),
                existingItems);

        List<PotentialPersonalSellTrade> resultingSellTrades = potentialTradeFactory.getResultingPersonalSellTrades(
                personalItems,
                manageableUser.getResaleLocks(),
                manageableUser.getSoldIn24h(),
                configTrades.getSellSlots(),
                configTrades.getSellLimit());
        List<PotentialPersonalBuyTrade> resultingBuyTrades = potentialTradeFactory.getResultingPersonalBuyTrades(
                personalItems,
                manageableUser.getCreditAmount(),
                manageableUser.getBoughtIn24h(),
                configTrades.getBuySlots(),
                configTrades.getBuyLimit());

        List<CentralTradeManagerCommand> commands = new ArrayList<>(centralTradeManagerCommandFactory.createCommandsForCentralTradeManagerForUser(
                resultingSellTrades,
                currentSellTrades,
                resultingBuyTrades,
                currentBuyTrades,
                manageableUser.getId(),
                manageableUser.toAuthorizationDTO()));

        for (CentralTradeManagerCommand command : commands.stream().sorted().toList()) {
            tradeManagementCommandsExecutor.executeCommand(command);
        }
    }
}
