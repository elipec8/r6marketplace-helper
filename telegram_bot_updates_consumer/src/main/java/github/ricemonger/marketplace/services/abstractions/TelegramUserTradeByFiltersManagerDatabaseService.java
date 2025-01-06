package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.TradeByFiltersManagerDoesntExistException;

import java.util.List;

public interface TelegramUserTradeByFiltersManagerDatabaseService {
    void save(String chatId, TradeByFiltersManager tradeManager) throws TelegramUserDoesntExistException;

    void invertEnabledFlagById(String chatId, String name) throws TelegramUserDoesntExistException, TradeByFiltersManagerDoesntExistException;

    void deleteById(String chatId, String name) throws TelegramUserDoesntExistException;

    TradeByFiltersManager findById(String chatId, String name) throws TelegramUserDoesntExistException, TradeByFiltersManagerDoesntExistException;

    List<TradeByFiltersManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
