package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemFilterServiceTest {
    @Autowired
    private ItemFilterService itemFilterService;
    @MockBean
    private TelegramUserItemFilterDatabaseService telegramUserItemFilterDatabaseService;

    @Test
    public void save_should_handle_to_db_service() {
        ItemFilter itemFilter = new ItemFilter();
        String chatId = "chatId";

        itemFilterService.save(chatId, itemFilter);

        verify(telegramUserItemFilterDatabaseService).save(same(chatId), same(itemFilter));
    }

    @Test
    public void deleteById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        itemFilterService.deleteById(chatId, name);

        verify(telegramUserItemFilterDatabaseService).deleteById(same(chatId), same(name));
    }

    @Test
    public void getById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        ItemFilter itemFilter = new ItemFilter();

        when(telegramUserItemFilterDatabaseService.findById(chatId, name)).thenReturn(itemFilter);

        assertSame(itemFilter, itemFilterService.getById(chatId, name));
    }

    @Test
    public void getAllByChatId_should_handle_to_db_service() {
        String chatId = "chatId";

        List list = Mockito.mock(List.class);

        when(telegramUserItemFilterDatabaseService.findAllByChatId(chatId)).thenReturn(list);

        assertSame(list, itemFilterService.getAllByChatId(chatId));
    }

    @Test
    public void getAllNamesByChatId_should_handle_to_db_service() {
        String chatId = "chatId";
        List list = Mockito.mock(List.class);

        when(telegramUserItemFilterDatabaseService.findAllNamesByChatId(chatId)).thenReturn(list);

        assertSame(list, itemFilterService.getAllNamesByChatId(chatId));
    }
}