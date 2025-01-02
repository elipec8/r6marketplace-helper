package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFilterService {

    private final TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    public void save(String chatId, ItemFilter itemFilter) {
        telegramUserItemFilterDatabaseService.save(chatId, itemFilter);
    }

    public void deleteById(String chatId, String name) {
        telegramUserItemFilterDatabaseService.deleteById(chatId, name);
    }

    public ItemFilter getById(String chatId, String name) {
        return telegramUserItemFilterDatabaseService.findById(chatId, name);
    }

    public List<ItemFilter> getAllByChatId(String chatId) {
        return telegramUserItemFilterDatabaseService.findAllByChatId(chatId);
    }

    public List<String> getAllNamesByChatId(String chatId) {
        return telegramUserItemFilterDatabaseService.findAllNamesByChatId(chatId);
    }
}
