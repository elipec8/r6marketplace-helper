package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;

import java.util.List;

public interface TelegramUserTradeByItemIdManagerDatabaseService {
    void save(String chatId, TradeByItemIdManager tradeManager) throws TelegramUserDoesntExistException;

    void deleteById(String chatId, String itemId) throws TelegramUserDoesntExistException;

    TradeByItemIdManager findById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeManagerByItemIdDoesntExistException;

    List<TradeByItemIdManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
