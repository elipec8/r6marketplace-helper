package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeManagerService {

    private final TelegramUserTradeByItemIdManagerDatabaseService telegramUserTradeByItemIdManagerDatabaseService;

    private final TelegramUserTradeByFiltersManagerDatabaseService telegramUserTradeByFiltersManagerDatabaseService;

    public void saveTradeByItemIdManager(String chatId, TradeByItemIdManager tradeManager) {
        telegramUserTradeByItemIdManagerDatabaseService.save(chatId, tradeManager);
    }

    public void saveTradeByFiltersManager(String chatId, TradeByFiltersManager tradeManager) {
        telegramUserTradeByFiltersManagerDatabaseService.save(chatId, tradeManager);
    }

    public void invertTradeByFiltersManagerEnabledFlagById(String chatId, String name) {
        telegramUserTradeByFiltersManagerDatabaseService.invertEnabledFlagById(chatId, name);
    }

    public void invertTradeByItemIdManagerEnabledFlagById(String chatId, String itemId) {
        telegramUserTradeByItemIdManagerDatabaseService.invertEnabledFlagById(chatId, itemId);
    }

    public void deleteTradeByItemIdManagerById(String chatId, String itemId) {
        telegramUserTradeByItemIdManagerDatabaseService.deleteById(chatId, itemId);
    }

    public void deleteTradeByFiltersManagerById(String chatId, String name) {
        telegramUserTradeByFiltersManagerDatabaseService.deleteById(chatId, name);
    }

    public TradeByItemIdManager getTradeByItemIdManagerById(String chatId, String itemId) {
        return telegramUserTradeByItemIdManagerDatabaseService.findById(chatId, itemId);
    }

    public TradeByFiltersManager getTradeByFiltersManagerById(String chatId, String name) {
        return telegramUserTradeByFiltersManagerDatabaseService.findById(chatId, name);
    }

    public List<TradeByItemIdManager> getAllTradeByItemIdManagersByChatId(String chatId) {
        return telegramUserTradeByItemIdManagerDatabaseService.findAllByChatId(chatId);
    }

    public List<TradeByFiltersManager> getAllTradeByFiltersManagersByChatId(String chatId) {
        return telegramUserTradeByFiltersManagerDatabaseService.findAllByChatId(chatId);
    }
}
