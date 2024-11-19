package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.TradeByItemIdManagerDoesntExistException;

import java.util.List;

public interface TelegramUserTradeByItemIdManagerDatabaseService {
    void save(String chatId, TradeByItemIdManager tradeManager) throws TelegramUserDoesntExistException;

    void invertEnabledFlagById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException;

    void deleteById(String chatId, String itemId) throws TelegramUserDoesntExistException;

    TradeByItemIdManager findById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException;

    List<TradeByItemIdManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
