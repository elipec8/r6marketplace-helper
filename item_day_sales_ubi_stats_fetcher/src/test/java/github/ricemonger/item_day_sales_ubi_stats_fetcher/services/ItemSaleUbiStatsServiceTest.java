package github.ricemonger.item_day_sales_ubi_stats_fetcher.services;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.services.abstractions.ItemSaleUbiStatsDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemSaleUbiStatsServiceTest {
    @Autowired
    private ItemSaleUbiStatsService itemSaleUbiStatsService;
    @MockBean
    private ItemSaleUbiStatsDatabaseService itemSaleUbiStatsDatabaseService;

    @Test
    public void saveAll_should_handle_to_service() {
        List mockedList = Mockito.mock(List.class);

        itemSaleUbiStatsService.insertAllItemSaleUbiStats(mockedList);

        verify(itemSaleUbiStatsDatabaseService).insertAll(mockedList);
    }
}