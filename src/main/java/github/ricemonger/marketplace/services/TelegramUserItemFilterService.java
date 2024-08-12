package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserItemFilterService {

    private final TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    public void saveItemFilter(String chatId, ItemFilter itemFilter) throws TelegramUserDoesntExistException {
        telegramUserItemFilterDatabaseService.save(chatId, itemFilter);
    }

    public void deleteItemFilterById(String chatId, String name) throws TelegramUserDoesntExistException {
        telegramUserItemFilterDatabaseService.deleteById(chatId, name);
    }

    public ItemFilter getItemFilterById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        return telegramUserItemFilterDatabaseService.findById(chatId, name);
    }

    public List<String> getAllUserItemFiltersNames(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserItemFilterDatabaseService.findAllByChatId(chatId).stream().map(ItemFilter::getName).toList();
    }
}
