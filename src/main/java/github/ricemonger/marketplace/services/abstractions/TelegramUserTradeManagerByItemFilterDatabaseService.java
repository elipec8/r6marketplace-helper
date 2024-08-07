package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeManagerByItemFilters;

import java.util.Collection;
import java.util.List;

public interface TelegramUserTradeManagerByItemFilterDatabaseService {
    void save(TradeManagerByItemFilters tradeManager);

    List<TradeManagerByItemFilters> findAll(String chatId);
}
