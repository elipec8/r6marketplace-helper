package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.item_stats_fetcher.services.abstractions.ItemDatabaseService;
import github.ricemonger.item_stats_fetcher.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemDatabaseService itemDatabaseService;
    @MockBean
    private ItemSaleDatabaseService itemSaleDatabaseService;

    @Test
    public void saveAllItemsMainFields_should_handle_to_service() {
        List<ItemMainFieldsI> list = List.of();

        itemService.saveAllItemsMainFields(list);

        verify(itemDatabaseService).saveAllItemsMainFields(same(list));
    }

    @Test
    public void saveAllItemsLastSales_should_handle_to_service() {
        List<SoldItemDetails> list = List.of();

        itemService.saveAllItemsLastSales(list);

        verify(itemSaleDatabaseService).saveAllItemsLastSales(same(list));
    }

    @Test
    public void updateAllItemsMinSellPrice_should_handle_to_service() {
        List list = mock(List.class);

        itemService.updateAllItemsMinSellPrice(list);

        verify(itemDatabaseService).updateAllItemsMinSellPrice(same(list));
    }
}