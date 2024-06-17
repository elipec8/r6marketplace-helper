package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemFilterServiceTest {
    @MockBean
    private ItemFilterDatabaseService itemFilterDatabaseService;

    @Autowired
    private ItemFilterService itemFilterService;

    @Test
    public void saveItemFilter_should_handle_to_db_service() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilterService.saveItemFilter(itemFilter);

        verify(itemFilterDatabaseService).save(same(itemFilter));
    }

    @Test
    public void getItemFilterById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        ItemFilter itemFilter = new ItemFilter();

        when(itemFilterDatabaseService.findById(chatId, name)).thenReturn(itemFilter);

        assertEquals(itemFilter, itemFilterService.getItemFilterById(chatId, name));

        verify(itemFilterDatabaseService).findById(chatId, name);
    }

    @Test
    public void deleteItemFilterById_should_handle_to_db_service() {
        String chatId = "chatId";
        String name = "name";

        itemFilterService.deleteItemFilterById(chatId, name);

        verify(itemFilterDatabaseService).deleteById(chatId, name);
    }

    @Test
    public void getAllItemFilterNamesForUser_should_handle_to_db_service() {
        String chatId = "chatId";

        ItemFilter f1 = new ItemFilter();
        f1.setName("f1");
        ItemFilter f2 = new ItemFilter();
        f2.setName("f2");

        Collection<String> expected = List.of("f1", "f2");

        when(itemFilterDatabaseService.findAllByChatId(chatId)).thenReturn(List.of(f1, f2));

        Collection<String> result = itemFilterService.getAllItemFilterNamesForUser(chatId);

        verify(itemFilterDatabaseService).findAllByChatId(chatId);

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}