package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemFilter;

import java.util.Collection;
import java.util.List;

public interface TelegramUserItemFilterDatabaseService {
    void save(String chatId, ItemFilter filter);

    void deleteById(String chatId, String name);

    ItemFilter findById(String chatId, String name);

    List<ItemFilter> findAllByChatId(String chatId);
}
