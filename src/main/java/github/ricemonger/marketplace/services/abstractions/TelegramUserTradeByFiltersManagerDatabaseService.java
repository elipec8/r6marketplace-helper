package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeByFiltersManager;

import java.util.List;

public interface TelegramUserTradeByFiltersManagerDatabaseService {
    void save(String chatId, TradeByFiltersManager tradeManager);

    List<TradeByFiltersManager> findAllByChatId(String chatId);
}
