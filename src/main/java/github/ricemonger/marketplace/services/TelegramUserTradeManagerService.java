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
public class TelegramUserTradeManagerService {

    private final TelegramUserTradeManagerByItemIdDatabaseService telegramUserTradeManagerByItemIdDatabaseService;

    private final TelegramUserTradeManagerByItemFilterDatabaseService telegramUserTradeManagerByItemFiltersDatabaseService;

    public void saveUserTradeManagerByItemId(String chatId, TradeManagerByItemId tradeManager) {
        telegramUserTradeManagerByItemIdDatabaseService.save(chatId, tradeManager);
    }

    public void saveUserTradeManagerByItemFilter(String chatId, TradeManagerByItemFilters tradeManager) {
        telegramUserTradeManagerByItemFiltersDatabaseService.save(chatId, tradeManager);
    }

    public void deleteUserTradeManagerByItemIdById(String chatId, String itemId) {
        telegramUserTradeManagerByItemIdDatabaseService.deleteById(chatId, itemId);
    }

    public TradeManagerByItemId getUserTradeManagerByItemIdById(String chatId, String itemId) {
        return telegramUserTradeManagerByItemIdDatabaseService.findById(chatId, itemId);
    }

    public Collection<TradeManagerByItemId> getAllUserTradeManagersByItemId(String chatId) {
        return telegramUserTradeManagerByItemIdDatabaseService.findAllByChatId(chatId);
    }

    public Collection<TradeManagerByItemFilters> getAllUserTradeManagersByItemFilters(String chatId) {
        return telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(chatId);
    }
}
