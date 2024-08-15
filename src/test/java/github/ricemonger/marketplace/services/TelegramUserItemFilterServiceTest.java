package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserItemFilterServiceTest {
    @Autowired
    private TelegramUserItemFilterService telegramUserItemFilterService;
    @MockBean
    private TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    @Test
    public void saveItemFilter_should_handle_to_db_service() {
        ItemFilter itemFilter = new ItemFilter();
        String chatId = "chatId";

        telegramUserItemFilterService.saveItemFilter(chatId, itemFilter);

        verify(telegramUserItemFilterDatabaseService).save(same(chatId), same(itemFilter));
    }

    @Test
    public void deleteItemFilterById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        telegramUserItemFilterService.deleteItemFilterById(chatId, name);

        verify(telegramUserItemFilterDatabaseService).deleteById(same(chatId), same(name));
    }

    @Test
    public void getItemFilterById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        ItemFilter itemFilter = new ItemFilter();

        when(telegramUserItemFilterDatabaseService.findById(chatId, name)).thenReturn(itemFilter);

        assertEquals(itemFilter, telegramUserItemFilterService.getItemFilterById(chatId, name));

        verify(telegramUserItemFilterDatabaseService).findById(same(chatId), same(name));
    }

    @Test
    public void getAllUser_ItemFiltersNames_should_handle_to_db_service() {
        String chatId = "chatId";
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("name");

        when(telegramUserItemFilterDatabaseService.findAllByChatId(chatId)).thenReturn(List.of(itemFilter));

        assertEquals(List.of("name"), telegramUserItemFilterService.getAllUserItemFiltersNames(chatId));

        verify(telegramUserItemFilterDatabaseService).findAllByChatId(same(chatId));
    }
}