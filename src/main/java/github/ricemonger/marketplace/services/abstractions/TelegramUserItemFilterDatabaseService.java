package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;

import java.util.List;

public interface TelegramUserItemFilterDatabaseService {
    void save(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException;

    void deleteById(String chatId, String name) throws TelegramUserDoesntExistException;

    ItemFilter findById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException;

    List<ItemFilter> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
