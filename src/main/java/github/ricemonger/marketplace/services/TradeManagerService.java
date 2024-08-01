package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemFilterDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TradeManagerService {

    private final TelegramUserTradeManagerByItemIdDatabaseService telegramUserTradeManagerByItemIdDatabaseService;

    private final TelegramUserTradeManagerByItemFilterDatabaseService tradeManagerByItemFiltersDatabaseService;

    public void saveTradeManagerByItemId(String chatId, TradeManagerByItemId tradeManager) {
        telegramUserTradeManagerByItemIdDatabaseService.save(chatId, tradeManager);
    }

    public void saveTradeManagerByItemFilter(String chatId, TradeManagerByItemFilters tradeManager) {
        tradeManagerByItemFiltersDatabaseService.save(tradeManager);
    }

    public void deleteTradeManagerByItemIdById(String chatId, String itemId) {
        telegramUserTradeManagerByItemIdDatabaseService.deleteById(chatId, itemId);
    }

    public TradeManagerByItemId getTradeManagerByItemIdById(String chatId, String itemId) {
        return telegramUserTradeManagerByItemIdDatabaseService.findById(chatId, itemId);
    }

    public Collection<TradeManagerByItemId> getAllTradeManagersByItemId(String chatId) {
        return telegramUserTradeManagerByItemIdDatabaseService.findAllByChatId(chatId);
    }

    public Collection<TradeManagerByItemFilters> getAllTradeManagersByItemFilters(String chatId) {
        return tradeManagerByItemFiltersDatabaseService.findAll(chatId);
    }
}
