package github.ricemonger.item_stats_fetcher.scheduled_tasks;

import github.ricemonger.item_stats_fetcher.services.ItemService;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.CommonQueryItemMinSellPricesGraphQlClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduledAllItemsMinSellPriceFetcherTest {
    @Autowired
    private ScheduledAllItemsMinSellPriceFetcher scheduledAllItemsMinSellPriceFetcher;
    @MockBean
    private CommonQueryItemMinSellPricesGraphQlClientService commonQueryItemMinSellPricesGraphQlClientService;
    @MockBean
    private ItemService itemService;

    @Test
    public void fetchAllItemMinSellPrices_should_save_graphQl_result_in_db() {
        List list = mock(List.class);
        when(commonQueryItemMinSellPricesGraphQlClientService.fetchAllItemMinSellPrices()).thenReturn(list);

        scheduledAllItemsMinSellPriceFetcher.fetchAllItemMinSellPrices();

        verify(itemService).updateAllItemsMinSellPrice(list);
    }
}