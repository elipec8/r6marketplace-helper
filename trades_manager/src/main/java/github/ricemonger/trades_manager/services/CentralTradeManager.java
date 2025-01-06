package github.ricemonger.trades_manager.services;

import github.ricemonger.marketplace.graphQl.personal_mutation_buy_create.PersonalMutationBuyCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_buy_update.PersonalMutationBuyUpdateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_cancel.PersonalMutationCancelGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_create.PersonalMutationSellCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_update.PersonalMutationSellUpdateGraphQlClientService;
import github.ricemonger.trades_manager.services.DTOs.*;
import github.ricemonger.trades_manager.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.trades_manager.services.factories.PersonalItemFactory;
import github.ricemonger.trades_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentralTradeManager {
    private final PersonalMutationBuyCreateGraphQlClientService personalMutationBuyCreateGraphQlClientService;
    private final PersonalMutationBuyUpdateGraphQlClientService personalMutationBuyUpdateGraphQlClientService;
    private final PersonalMutationSellCreateGraphQlClientService personalMutationSellCreateGraphQlClientService;
    private final PersonalMutationSellUpdateGraphQlClientService personalMutationSellUpdateGraphQlClientService;
    private final PersonalMutationCancelGraphQlClientService personalMutationCancelGraphQlClientService;
    private final NotificationService notificationService;
    private final CommonValuesService commonValuesService;
    private final ItemService itemService;
    private final UserService userService;
    private final PersonalItemFactory personalItemFactory;
    private final PotentialTradeFactory potentialTradeFactory;
    private final CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;

    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<ManageableUser> manageableUsers = userService.getAllManageableUsers();

        for (ManageableUser manageableUser : manageableUsers) {
            createAndExecuteCentralTradeManagerCommandsForUser(manageableUser, configTrades, items);
        }
    }

    private void createAndExecuteCentralTradeManagerCommandsForUser(ManageableUser manageableUser,
                                                                    ConfigTrades configTrades,
                                                                    Collection<Item> existingItems) {

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
            executeCentralTradeManagerCommand(command);
        }
    }

    private void executeCentralTradeManagerCommand(CentralTradeManagerCommand command) {
        switch (command.getCommandType()) {
            case BUY_ORDER_CANCEL -> {
                personalMutationCancelGraphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                               " cancelled", command.getItemName(), command.getOldPrice()));
            }
            case BUY_ORDER_UPDATE -> {
                personalMutationBuyUpdateGraphQlClientService.updateBuyOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your buy order price for item %s has been updated" +
                                                                                               "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
            }
            case BUY_ORDER_CREATE -> {
                personalMutationBuyCreateGraphQlClientService.createBuyOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                               " created", command.getItemName(), command.getNewPrice()));
            }
            case SELL_ORDER_CANCEL -> {
                personalMutationCancelGraphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                               " cancelled", command.getItemName(), command.getOldPrice()));

            }
            case SELL_ORDER_UPDATE -> {
                personalMutationSellUpdateGraphQlClientService.updateSellOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your sell order price for item %s has been updated" +
                                                                                               "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
            }
            case SELL_ORDER_CREATE -> {
                personalMutationSellCreateGraphQlClientService.createSellOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                notificationService.sendPrivateNotification(command.getUserId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                               " created", command.getItemName(), command.getNewPrice()));
            }
        }
    }
}
