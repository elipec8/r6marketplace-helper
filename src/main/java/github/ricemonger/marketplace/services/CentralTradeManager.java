package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.marketplace.services.factories.PersonalItemFactory;
import github.ricemonger.marketplace.services.factories.PotentialTradeFactory;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CentralTradeManager {
    private final GraphQlClientService graphQlClientService;
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
                new AuthorizationDTO(userForCentralTradeManager),
                userForCentralTradeManager.getChatId(),
                userForCentralTradeManager.getPrivateNotificationsEnabledFlag() == null ? false : userForCentralTradeManager.getPrivateNotificationsEnabledFlag()));

        for (CentralTradeManagerCommand command : commands.stream().sorted().toList()) {
            executeCentralTradeManagerCommand(command);
        }
    }

    private void executeCentralTradeManagerCommand(@NotNull CentralTradeManagerCommand command) {
        switch (command.getCommandType()) {
            case BUY_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " cancelled", command.getItemName(), command.getOldPrice()));
                }
            }
            case BUY_ORDER_UPDATE -> {
                graphQlClientService.updateBuyOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order price for item %s has been updated" +
                                                                                                 "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case BUY_ORDER_CREATE -> {
                graphQlClientService.createBuyOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " created", command.getItemName(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " cancelled", command.getItemName(), command.getOldPrice()));
                }
            }
            case SELL_ORDER_UPDATE -> {
                graphQlClientService.updateSellOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order price for item %s has been updated" +
                                                                                                 "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CREATE -> {
                graphQlClientService.createSellOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.getPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " created", command.getItemName(), command.getNewPrice()));
                }
            }
        }
    }
}
