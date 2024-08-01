package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemFilterService {

    private final TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    public void saveItemFilter(String chatId, ItemFilter itemFilter) {
        telegramUserItemFilterDatabaseService.save(chatId, itemFilter);
    }

    public void deleteItemFilterById(String chatId, String name) {
        telegramUserItemFilterDatabaseService.deleteById(chatId, name);
    }

    public ItemFilter getItemFilterById(String chatId, String name) {
        return telegramUserItemFilterDatabaseService.findById(chatId, name);
    }

    public Collection<String> getAllItemFilterNamesForUser(String chatId) {
        return telegramUserItemFilterDatabaseService.findAllByChatId(chatId).stream().map(ItemFilter::getName).toList();
    }
}
