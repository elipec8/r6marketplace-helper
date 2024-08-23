package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeByFiltersManagerDoesntExistException;

import java.util.List;

public interface TelegramUserTradeByFiltersManagerDatabaseService {
    void save(String chatId, TradeByFiltersManager tradeManager) throws TelegramUserDoesntExistException;

    void deleteById(String chatId, String name) throws TelegramUserDoesntExistException;

    TradeByFiltersManager findById(String chatId, String name) throws TelegramUserDoesntExistException, TradeByFiltersManagerDoesntExistException;

    List<TradeByFiltersManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
