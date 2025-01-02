package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemDatabaseService itemDatabaseService;

    @Test
    public void getItemById_should_return_service_result() {
        Item item = new Item();
        item.setItemId("1");

        when(itemDatabaseService.findById("1")).thenReturn(item);

        assertSame(item, itemService.getItemById("1"));
    }

    @Test
    public void getAllItemsByFilter_should_return_service_result_after_filtering() {
        try (MockedStatic<ItemFilter> itemFilterMock = mockStatic(ItemFilter.class)) {
            Item item1 = new Item();
            item1.setItemId("1");
            Item item2 = new Item();
            item2.setItemId("2");

            List<Item> items = List.of(item1, item2);
            List<Item> filteredItems = List.of(item1);

            ItemFilter itemFilter = new ItemFilter();
            List<ItemFilter> filters = List.of(itemFilter);

            when(itemDatabaseService.findAll()).thenReturn(items);


            itemFilterMock.when(() -> ItemFilter.filterItems(same(items), same(filters))).thenReturn(filteredItems);

            assertSame(filteredItems, itemService.getAllItemsByFilters(filters));
        }
    }
}