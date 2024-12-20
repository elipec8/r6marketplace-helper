package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserItemFilterService {

    private final TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    public void save(String chatId, ItemFilter itemFilter) throws TelegramUserDoesntExistException {
        telegramUserItemFilterDatabaseService.save(chatId, itemFilter);
    }

    public void deleteById(String chatId, String name) throws TelegramUserDoesntExistException {
        telegramUserItemFilterDatabaseService.deleteById(chatId, name);
    }

    public ItemFilter getById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        return telegramUserItemFilterDatabaseService.findById(chatId, name);
    }

    public List<ItemFilter> getAllForTelegramUser(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserItemFilterDatabaseService.findAllByChatId(chatId);
    }

    public List<String> getAllItemFilterNamesForTelegramUser(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserItemFilterDatabaseService.findAllByChatId(chatId).stream().map(ItemFilter::getName).toList();
    }
}
