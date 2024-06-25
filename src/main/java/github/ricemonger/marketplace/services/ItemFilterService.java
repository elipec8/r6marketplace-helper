package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemFilterService {

    private final ItemFilterDatabaseService itemFilterDatabaseService;

    public void saveItemFilter(String chatId, ItemFilter itemFilter) {
        itemFilterDatabaseService.save(chatId,itemFilter);
    }

    public void deleteItemFilterById(String chatId, String name) {
        itemFilterDatabaseService.deleteById(chatId, name);
    }

    public ItemFilter getItemFilterById(String chatId, String name) {
        return itemFilterDatabaseService.findById(chatId, name);
    }

    public Collection<String> getAllItemFilterNamesForUser(String chatId) {
        return itemFilterDatabaseService.findAllByUserId(chatId).stream().map(ItemFilter::getName).toList();
    }
}
