package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeManagerByItemId;

import java.util.List;

public interface TelegramUserTradeManagerByItemIdDatabaseService {
    void save(String chatId, TradeManagerByItemId tradeManager);

    void deleteById(String chatId, String itemId);

    TradeManagerByItemId findById(String chatId, String itemId);

    List<TradeManagerByItemId> findAllByChatId(String chatId);
}
