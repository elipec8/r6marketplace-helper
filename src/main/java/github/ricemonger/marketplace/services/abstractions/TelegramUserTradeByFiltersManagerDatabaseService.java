package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;

import java.util.List;

public interface TelegramUserTradeByFiltersManagerDatabaseService {
    void save(String chatId, TradeByFiltersManager tradeManager) throws TelegramUserDoesntExistException;

    List<TradeByFiltersManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
