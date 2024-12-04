package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.items.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CentralTradeManager {

    private final UserService userService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    private final GraphQlClientService graphQlClientService;

    private final TelegramBotService telegramBotService;

    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<UserForCentralTradeManager> userForCentralTradeManagers = userService.getAllUserForCentralTradeManagerDTOs(items);

        for (UserForCentralTradeManager userForCentralTradeManager : userForCentralTradeManagers) {
            createAndExecuteCentralTradeManagerCommandsForUser(userForCentralTradeManager, configTrades);
        }
    }

    private void createAndExecuteCentralTradeManagerCommandsForUser(UserForCentralTradeManager userForCentralTradeManager, ConfigTrades configTrades) {
        Set<CentralTradeManagerCommand> sortedCommands = new TreeSet<>(Comparator.comparing(CentralTradeManagerCommand::getCommandType));

        Long userId = userForCentralTradeManager.getId();
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(userForCentralTradeManager);
        String chatId = userForCentralTradeManager.getChatId();
        boolean privateNotificationsEnabledFlag = userForCentralTradeManager.getPrivateNotificationsEnabledFlag();

        int saleExpiresAfterMinutes = configTrades.getSaleExpiresAfterMinutes();
        int buySlots = configTrades.getBuySlots();
        int sellSlots = configTrades.getSellSlots();
        int buyLimit = configTrades.getBuyLimit();
        int sellLimit = configTrades.getSellLimit();
        int feePercentage = configTrades.getFeePercentage();

        int boughtIn24h = userForCentralTradeManager.getBoughtIn24h();
        int soldIn24h = userForCentralTradeManager.getSoldIn24h();
        int creditAmount = userForCentralTradeManager.getCreditAmount();
        List<String> ownedItemsIds = userForCentralTradeManager.getOwnedItemsIds();
        List<UbiTrade> currentBuyTrades = userForCentralTradeManager.getCurrentBuyTrades();
        List<UbiTrade> currentSellTrades = userForCentralTradeManager.getCurrentSellTrades();
        List<UbiTrade> finishedTrades = userForCentralTradeManager.getFinishedTrades();




        for(CentralTradeManagerCommand command : sortedCommands) {
            executeCentralTradeManagerCommand(command);
        }
    }

    private void executeCentralTradeManagerCommand(CentralTradeManagerCommand command) {
        switch (command.getCommandType()) {
            case BUY_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " cancelled",
                            command.getItemName(), command.getOldPrice()));
                }
            }
            case BUY_ORDER_UPDATE -> {
                graphQlClientService.updateBuyOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order price for item %s has been updated" +
                                                                                                 "from %d to %d",
                            command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case BUY_ORDER_CREATE -> {
                graphQlClientService.createBuyOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " created",
                            command.getItemName(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " cancelled",
                            command.getItemName(), command.getOldPrice()));
                }
            }
            case SELL_ORDER_UPDATE -> {
                graphQlClientService.updateSellOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order price for item %s has been updated" +
                                                                                                 "from %d to %d",
                            command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CREATE -> {
                graphQlClientService.createSellOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " created",
                            command.getItemName(), command.getNewPrice()));
                }
            }
        }
    }
}
