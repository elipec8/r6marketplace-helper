package github.ricemonger.trades_manager.services;

import github.ricemonger.marketplace.graphQl.personal_mutation_buy_create.PersonalMutationBuyCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_buy_update.PersonalMutationBuyUpdateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_cancel.PersonalMutationCancelGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_create.PersonalMutationSellCreateGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_mutation_sell_update.PersonalMutationSellUpdateGraphQlClientService;
import github.ricemonger.trades_manager.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.trades_manager.services.factories.PersonalItemFactory;
import github.ricemonger.trades_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentralTradeManager {
    private final PersonalMutationBuyCreateGraphQlClientService personalMutationBuyCreateGraphQlClientService;
    private final PersonalMutationBuyUpdateGraphQlClientService personalMutationBuyUpdateGraphQlClientService;
    private final PersonalMutationSellCreateGraphQlClientService personalMutationSellCreateGraphQlClientService;
    private final PersonalMutationSellUpdateGraphQlClientService personalMutationSellUpdateGraphQlClientService;
    private final PersonalMutationCancelGraphQlClientService personalMutationCancelGraphQlClientService;
    private final TelegramBotService telegramBotService;
    private final CommonValuesService commonValuesService;
    private final ItemService itemService;
    private final UserService userService;
    private final PersonalItemFactory personalItemFactory;
    private final PotentialTradeFactory potentialTradeFactory;
    private final CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;

    public void manageAllUsersTrades(Collection<UbiAccountStats> updatedUbiAccountStats) {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<ManageableUser> manageableUsers = userService.getAllManageableUsers();

        List<UserForCentralTradeManager> usersForCentralTradeManager = new LinkedList<>();

        for (ManageableUser manageableUser : manageableUsers) {
            UbiAccountStats linkedUbiAccount =
                    updatedUbiAccountStats.stream().filter(u -> u != null && manageableUser != null && Objects.equals(u.getUbiProfileId(),
                            manageableUser.getUbiProfileId())).findFirst().orElse(null);

            if (manageableUser != null && linkedUbiAccount != null) {
                usersForCentralTradeManager.add(new UserForCentralTradeManager(manageableUser, linkedUbiAccount));
            }
        }

        for (UserForCentralTradeManager userForCentralTradeManager : usersForCentralTradeManager) {
            createAndExecuteCentralTradeManagerCommandsForUser(userForCentralTradeManager, configTrades, items);
        }
    }

    private void createAndExecuteCentralTradeManagerCommandsForUser(@NotNull UserForCentralTradeManager userForCentralTradeManager,
                                                                    @NotNull ConfigTrades configTrades,
                                                                    Collection<Item> existingItems) {
        List<UbiTrade> currentSellTrades = userForCentralTradeManager.getCurrentSellTrades();
        List<UbiTrade> currentBuyTrades = userForCentralTradeManager.getCurrentBuyTrades();

        Set<PersonalItem> personalItems = personalItemFactory.getPersonalItemsForUser(
                userForCentralTradeManager.getTradeByFiltersManagers(),
                userForCentralTradeManager.getTradeByItemIdManagers(),
                currentSellTrades,
                currentBuyTrades,
                userForCentralTradeManager.getOwnedItemsIds(),
                existingItems);

        List<PotentialPersonalSellTrade> resultingSellTrades = potentialTradeFactory.getResultingPersonalSellTrades(
                personalItems,
                userForCentralTradeManager.getResaleLocks(),
                userForCentralTradeManager.getSoldIn24h(),
                configTrades.getSellSlots(),
                configTrades.getSellLimit());
        List<PotentialPersonalBuyTrade> resultingBuyTrades = potentialTradeFactory.getResultingPersonalBuyTrades(
                personalItems,
                userForCentralTradeManager.getCreditAmount(),
                userForCentralTradeManager.getBoughtIn24h(),
                configTrades.getBuySlots(),
                configTrades.getBuyLimit());

        List<CentralTradeManagerCommand> commands = new ArrayList<>(centralTradeManagerCommandFactory.createCommandsForCentralTradeManagerForUser(
                resultingSellTrades,
                currentSellTrades,
                resultingBuyTrades,
                currentBuyTrades,
                userForCentralTradeManager.getId(),
                new AuthorizationDTO(userForCentralTradeManager)));

        for (CentralTradeManagerCommand command : commands.stream().sorted().toList()) {
            log.info("Executing command: {}", command);
            //executeCentralTradeManagerCommand(command);
        }
    }

    private void executeCentralTradeManagerCommand(@NotNull CentralTradeManagerCommand command) {
        switch (command.getCommandType()) {
            case BUY_ORDER_CANCEL -> {
                personalMutationCancelGraphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                  " cancelled", command.getItemName(), command.getOldPrice()));
            }
            case BUY_ORDER_UPDATE -> {
                personalMutationBuyUpdateGraphQlClientService.updateBuyOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your buy order price for item %s has been updated" +
                                                                                                  "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
            }
            case BUY_ORDER_CREATE -> {
                personalMutationBuyCreateGraphQlClientService.createBuyOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                  " created", command.getItemName(), command.getNewPrice()));
            }
            case SELL_ORDER_CANCEL -> {
                personalMutationCancelGraphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                  " cancelled", command.getItemName(), command.getOldPrice()));

            }
            case SELL_ORDER_UPDATE -> {
                personalMutationSellUpdateGraphQlClientService.updateSellOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your sell order price for item %s has been updated" +
                                                                                                  "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
            }
            case SELL_ORDER_CREATE -> {
                personalMutationSellCreateGraphQlClientService.createSellOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                    telegramBotService.sendPrivateNotification(command.getUserId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                  " created", command.getItemName(), command.getNewPrice()));
            }
        }
    }
}
