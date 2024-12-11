package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CentralTradeManager {

    public static final int TRADE_MANAGER_FIXED_RATE_MINUTES = 1;

    public static final double TRADE_MANAGER_PRICE_POW_FACTOR = 2.0;

    public static final double TRADE_MANAGER_TIME_POW_FACTOR = 0.6;


    private final UserService userService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    private final GraphQlClientService graphQlClientService;

    private final TelegramBotService telegramBotService;

    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<UserForCentralTradeManager> userForCentralTradeManagers = userService.getAllUserForCentralTradeManager();

        for (UserForCentralTradeManager userForCentralTradeManager : userForCentralTradeManagers) {
            createAndExecuteCentralTradeManagerCommandsForUser(userForCentralTradeManager, configTrades, items);
        }
    }

    private void createAndExecuteCentralTradeManagerCommandsForUser(UserForCentralTradeManager userForCentralTradeManager,
                                                                    ConfigTrades configTrades, Collection<Item> existingItems) {

        int buySlots = configTrades.getBuySlots();
        int sellSlots = configTrades.getSellSlots();
        int buyLimit = configTrades.getBuyLimit();
        int sellLimit = configTrades.getSellLimit();
        int feePercentage = configTrades.getFeePercentage();

        Long userId = userForCentralTradeManager.getId();
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(userForCentralTradeManager);
        String chatId = userForCentralTradeManager.getChatId();
        boolean privateNotificationsEnabledFlag = userForCentralTradeManager.getPrivateNotificationsEnabledFlag();

        int boughtIn24h = userForCentralTradeManager.getBoughtIn24h();
        int soldIn24h = userForCentralTradeManager.getSoldIn24h();
        int creditAmount = userForCentralTradeManager.getCreditAmount();
        List<UbiTrade> currentBuyTrades = userForCentralTradeManager.getCurrentBuyTrades();
        List<UbiTrade> currentSellTrades = userForCentralTradeManager.getCurrentSellTrades();
        List<ItemResaleLockWithUbiAccount> resaleLocks = userForCentralTradeManager.getResaleLocks();
        Set<ItemForCentralTradeManager> itemsForCentralTradeManager =
                userForCentralTradeManager.getItemForCentralTradeManagerFromTradeManagersByPriority(existingItems, feePercentage);

        List<TradeForCentralTradeManager> resultingBuyTrades = getResultingBuyTrades(itemsForCentralTradeManager, currentBuyTrades,
                buySlots, buyLimit, boughtIn24h, creditAmount);

        List<TradeForCentralTradeManager> resultingSellTrades = getResultingSellTrades(itemsForCentralTradeManager, currentSellTrades, resaleLocks,
                sellSlots, sellLimit, soldIn24h);

        List<CentralTradeManagerCommand> sortedCommands = new ArrayList<>(createCommandsForUser(resultingBuyTrades, currentBuyTrades, resultingSellTrades, currentSellTrades, userId, authorizationDTO, chatId,
                privateNotificationsEnabledFlag));
        sortedCommands.sort(Comparator.comparing(CentralTradeManagerCommand::getCommandType));

        for (CentralTradeManagerCommand command : sortedCommands) {
            executeCentralTradeManagerCommand(command);
        }
    }

    private List<TradeForCentralTradeManager> getResultingBuyTrades(Collection<ItemForCentralTradeManager> itemsForCentralTradeManager,
                                                                    Collection<UbiTrade> currentBuyTrades,
                                                                    int buySlots,
                                                                    int buyLimit,
                                                                    int boughtIn24h,
                                                                    int creditAmount) {

        List<TradeForCentralTradeManager> sortedPotentialBuyTrades = itemsForCentralTradeManager.stream()
                .filter(item -> item.getTradePriority() > 0)
                .filter(item -> item.getTradeOperationType() == TradeOperationType.BUY || item.getTradeOperationType() == TradeOperationType.BUY_AND_SELL)
                .filter(item -> !item.getIsOwned())
                .map(TradeForCentralTradeManager::new)
                .sorted()
                .toList();

        List<TradeForCentralTradeManager> sortedExistingBuyTrades = currentBuyTrades.stream()
                .map(TradeForCentralTradeManager::new)
                .sorted()
                .toList();

        List<TradeForCentralTradeManager> sortedBuyTrades = new ArrayList<>();
        sortedBuyTrades.addAll(sortedPotentialBuyTrades);
        sortedBuyTrades.addAll(sortedExistingBuyTrades);

        int prognosedCreditAmount = creditAmount;
        int newTradesLimit = buyLimit - boughtIn24h;

        List<TradeForCentralTradeManager> resultingBuyTrades = new ArrayList<>();

        for (TradeForCentralTradeManager trade : sortedBuyTrades) {
            if (resultingBuyTrades.size() >= buySlots) {
                break;
            }

            boolean isSameItemTradeAlreadyInResult = resultingBuyTrades.stream().anyMatch(existingTrade -> existingTrade.getItemId().equals(trade.getItemId()));

            if (isSameItemTradeAlreadyInResult) {
                continue;
            } else if (trade.getAlreadyExists()) {
                resultingBuyTrades.add(trade);
            } else {
                TradeForCentralTradeManager sameItemExistingTrade =
                        sortedExistingBuyTrades.stream().filter(existingTrade -> existingTrade.getItemId().equals(trade.getItemId())).findFirst().orElse(null);
                if (sameItemExistingTrade != null && prognosedCreditAmount >= trade.getPrice() - sameItemExistingTrade.getPrice()) {
                    prognosedCreditAmount -= trade.getPrice();
                    prognosedCreditAmount += sameItemExistingTrade.getPrice();
                    resultingBuyTrades.add(trade);
                } else if (sameItemExistingTrade == null && newTradesLimit > 0 && prognosedCreditAmount >= trade.getPrice()) {
                    prognosedCreditAmount -= trade.getPrice();
                    newTradesLimit--;
                    resultingBuyTrades.add(trade);
                }
            }
        }
        return resultingBuyTrades;
    }

    private List<TradeForCentralTradeManager> getResultingSellTrades(Collection<ItemForCentralTradeManager> itemsForCentralTradeManager,
                                                                     Collection<UbiTrade> currentSellTrades,
                                                                     Collection<ItemResaleLockWithUbiAccount> resaleLocks,
                                                                     int sellSlots,
                                                                     int sellLimit,
                                                                     int soldIn24h) {
        List<TradeForCentralTradeManager> sortedPotentialSellTrades = itemsForCentralTradeManager.stream()
                .filter(item -> item.getTradePriority() > 0)
                .filter(item -> item.getTradeOperationType() == TradeOperationType.SELL || item.getTradeOperationType() == TradeOperationType.BUY_AND_SELL)
                .filter(ItemForCentralTradeManager::getIsOwned)
                .filter(item -> resaleLocks.stream().noneMatch(lock -> lock.getItemId().equals(item.getItemId())))
                .map(TradeForCentralTradeManager::new)
                .sorted()
                .toList();

        List<TradeForCentralTradeManager> sortedExistingSellTrades = currentSellTrades.stream()
                .map(TradeForCentralTradeManager::new)
                .sorted()
                .toList();

        List<TradeForCentralTradeManager> sortedSellTrades = new ArrayList<>();
        sortedSellTrades.addAll(sortedPotentialSellTrades.stream().limit(sellLimit - soldIn24h).toList());
        sortedSellTrades.addAll(sortedExistingSellTrades);

        return sortedSellTrades.stream()
                .limit(sellSlots)
                .toList();
    }

    private List<CentralTradeManagerCommand> createCommandsForUser(Collection<TradeForCentralTradeManager> resultingBuyTrades,
                                                                  Collection<UbiTrade> currentBuyTrades,
                                                                  Collection<TradeForCentralTradeManager> resultingSellTrades,
                                                                  Collection<UbiTrade> currentSellTrades,
                                                                  Long userId, AuthorizationDTO authorizationDTO, String chatId, boolean privateNotificationsEnabledFlag) {
        List<CentralTradeManagerCommand> commands = new LinkedList<>();

        for (TradeForCentralTradeManager trade : resultingBuyTrades) {
            UbiTrade existingTrade = currentBuyTrades.stream().filter(currentTrade -> currentTrade.getItemId().equals(trade.getItemId())).findFirst().orElse(null);
            if (existingTrade != null && !existingTrade.getProposedPaymentPrice().equals(trade.getPrice())) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.BUY_ORDER_UPDATE,
                        trade.getItemId(), trade.getItem().getName(), existingTrade.getTradeId(), existingTrade.getProposedPaymentPrice(), trade.getPrice()));
            } else if (existingTrade == null) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.BUY_ORDER_CREATE,
                        trade.getItemId(), trade.getItem().getName(), trade.getPrice()));
            }
        }
        for (UbiTrade trade : currentBuyTrades) {
            if (resultingBuyTrades.stream().noneMatch(resultingTrade -> resultingTrade.getItemId().equals(trade.getItemId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.BUY_ORDER_CANCEL,
                        trade.getItemId(), trade.getItem().getName(), trade.getTradeId(), trade.getProposedPaymentPrice()));
            }
        }

        for (TradeForCentralTradeManager trade : resultingSellTrades) {
            UbiTrade existingTrade = currentSellTrades.stream().filter(currentTrade -> currentTrade.getItemId().equals(trade.getItemId())).findFirst().orElse(null);
            if (existingTrade != null && !existingTrade.getProposedPaymentPrice().equals(trade.getPrice())) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.SELL_ORDER_UPDATE,
                        trade.getItemId(), trade.getItem().getName(), existingTrade.getTradeId(), existingTrade.getProposedPaymentPrice(), trade.getPrice()));
            } else if (existingTrade == null) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.SELL_ORDER_CREATE,
                        trade.getItemId(), trade.getItem().getName(), trade.getPrice()));
            }
        }

        for (UbiTrade trade : currentSellTrades) {
            if (resultingSellTrades.stream().noneMatch(resultingTrade -> resultingTrade.getItemId().equals(trade.getItemId()))) {
                commands.add(new CentralTradeManagerCommand(userId, authorizationDTO, chatId, privateNotificationsEnabledFlag, CentralTradeManagerCommandType.SELL_ORDER_CANCEL,
                        trade.getItemId(), trade.getItem().getName(), trade.getTradeId(), trade.getProposedPaymentPrice()));
            }
        }

        return commands;

    }

    private void executeCentralTradeManagerCommand(CentralTradeManagerCommand command) {
        switch (command.getCommandType()) {
            case BUY_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " cancelled", command.getItemName(), command.getOldPrice()));
                }
            }
            case BUY_ORDER_UPDATE -> {
                graphQlClientService.updateBuyOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order price for item %s has been updated" +
                                                                                                 "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case BUY_ORDER_CREATE -> {
                graphQlClientService.createBuyOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your buy order for item %s with price %d has been" +
                                                                                                 " created", command.getItemName(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CANCEL -> {
                graphQlClientService.cancelOrderForUser(command.getAuthorizationDTO(), command.getTradeId());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " cancelled", command.getItemName(), command.getOldPrice()));
                }
            }
            case SELL_ORDER_UPDATE -> {
                graphQlClientService.updateSellOrderForUser(command.getAuthorizationDTO(), command.getTradeId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order price for item %s has been updated" +
                                                                                                 "from %d to %d", command.getItemName(), command.getOldPrice(), command.getNewPrice()));
                }
            }
            case SELL_ORDER_CREATE -> {
                graphQlClientService.createSellOrderForUser(command.getAuthorizationDTO(), command.getItemId(), command.getNewPrice());
                if (command.isPrivateNotificationsEnabledFlag()) {
                    telegramBotService.sendNotificationToUser(command.getChatId(), String.format("Your sell order for item %s with price %d has been" +
                                                                                                 " created", command.getItemName(), command.getNewPrice()));
                }
            }
        }
    }
}
