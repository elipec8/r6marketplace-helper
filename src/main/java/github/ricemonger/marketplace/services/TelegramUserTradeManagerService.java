package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemFilterDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeManagerService {

    private final TelegramUserTradeManagerByItemIdDatabaseService telegramUserTradeManagerByItemIdDatabaseService;

    private final TelegramUserTradeManagerByItemFilterDatabaseService telegramUserTradeManagerByItemFiltersDatabaseService;

    public void saveUserTradeManagerByItemId(String chatId, TradeManagerByItemId tradeManager) throws TelegramUserDoesntExistException {
        telegramUserTradeManagerByItemIdDatabaseService.save(chatId, tradeManager);
    }

    public void saveUserTradeManagerByItemFilters(String chatId, TradeManagerByItemFilters tradeManager) throws TelegramUserDoesntExistException {
        telegramUserTradeManagerByItemFiltersDatabaseService.save(chatId, tradeManager);
    }

    public void deleteUserTradeManagerByItemIdById(String chatId, String itemId) throws TelegramUserDoesntExistException {
        telegramUserTradeManagerByItemIdDatabaseService.deleteById(chatId, itemId);
    }

    public TradeManagerByItemId getUserTradeManagerByItemIdById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeManagerByItemIdDoesntExistException {
        return telegramUserTradeManagerByItemIdDatabaseService.findById(chatId, itemId);
    }

    public List<TradeManagerByItemId> getAllUserTradeManagersByItemId(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeManagerByItemIdDatabaseService.findAllByChatId(chatId);
    }

    public List<TradeManagerByItemFilters> getAllUserTradeManagersByItemFilters(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserTradeManagerByItemFiltersDatabaseService.findAllByChatId(chatId);
    }
}
