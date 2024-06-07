package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemFilter;

import java.util.Collection;

public interface ItemFilterDatabaseService {
    Collection<ItemFilter> findAllItemFiltersByChatId(String chatId);

    void saveItemFilter(ItemFilter filter);

    ItemFilter findItemFilterById(String chatId, String name);

    void removeItemFilterById(String chatId, String name);

    Collection<String> getAllItemFilterNamesForUser(String chatId);
}
