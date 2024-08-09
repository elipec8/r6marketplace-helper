package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeManagerByItemFilters;

import java.util.List;

public interface TelegramUserTradeManagerByItemFilterDatabaseService {
    void save(String chatId, TradeManagerByItemFilters tradeManager);

    List<TradeManagerByItemFilters> findAllByChatId(String chatId);
}
