package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemStatsServiceTest {

    @MockBean
    private ItemDatabaseService itemDatabaseService;

    @Autowired
    private ItemStatsService itemStatsService;

    @Test
    public void saveAll_should_call_saveAllItemsAndItemSales() {
        itemStatsService.saveAll(null);

        verify(itemDatabaseService).saveAllItems(null);
    }

    @Test
    public void calculateItemsSaleHistoryStats_should_call_save_all_histories() {
        itemStatsService.calculateItemsSaleHistoryStats();

        verify(itemDatabaseService).saveAllItemSaleHistoryStats(any());
    }
}