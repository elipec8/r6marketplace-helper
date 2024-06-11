package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFilterService {

    private final ItemFilterDatabaseService itemFilterDatabaseService;

    public void saveItemFilter(ItemFilter itemFilter) {
        itemFilterDatabaseService.saveItemFilter(itemFilter);
    }

    public ItemFilter getItemFilterById(String chatId, String name) {
        return itemFilterDatabaseService.findItemFilterById(chatId, name);
    }

    public void removeItemFilterById(String chatId, String name) {
        itemFilterDatabaseService.removeItemFilterById(chatId, name);
    }

    public Collection<String> getAllItemFilterNamesForUser(String chatId) {
        return itemFilterDatabaseService.findAllItemFilterNamesByChatId(chatId);
    }
}
