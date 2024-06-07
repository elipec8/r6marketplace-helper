package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemFilter;

import java.util.Collection;

public interface ItemFilterDatabaseService {
    Collection<ItemFilter> findAllItemFiltersByChatId(String chatId);

    void saveItemFilter(ItemFilter filter);
}
