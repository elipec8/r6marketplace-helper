package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeManagerService {

    private final TelegramUserTradeByItemIdManagerDatabaseService telegramUserTradeByItemIdManagerDatabaseService;

    private final TelegramUserTradeByFiltersManagerDatabaseService telegramUserTradeManagerByItemFiltersDatabaseService;

    public void saveUserTradeByItemIdManager(String chatId, TradeByItemIdManager tradeManager) throws TelegramUserDoesntExistException {
        telegramUserTradeByItemIdManagerDatabaseService.save(chatId, tradeManager);
    }

    public void saveUserTradeByFiltersManager(String chatId, TradeByFiltersManager tradeManager) throws TelegramUserDoesntExistException {
        telegramUserTradeManagerByItemFiltersDatabaseService.save(chatId, tradeManager);
    }

    public void deleteUserTradeByItemIdManagerById(String chatId, String itemId) throws TelegramUserDoesntExistException {
        telegramUserTradeByItemIdManagerDatabaseService.deleteById(chatId, itemId);
    }

    public TradeByItemIdManager getUserTradeByItemIdManagerById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeManagerByItemIdDoesntExistException {
        return telegramUserTradeByItemIdManagerDatabaseService.findById(chatId, itemId);
    }

    public List<TradeByItemIdManager> getAllUserTradeByItemIdManagers(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeByItemIdManagerDatabaseService.findAllByChatId(chatId);
    }

    public List<TradeByFiltersManager> getAllUserTradeByFiltersManagers(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(chatId);
    }
}
