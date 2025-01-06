package github.ricemonger.item_day_sales_ubi_stats_fetcher.scheduled_tasks;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.services.ItemSaleUbiStatsService;
import github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.CommonQueryItemsSaleStatsGraphQlClientService;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledAllItemsUbiStatsFetcherTest {
    @Autowired
    private ScheduledAllItemsUbiStatsFetcher scheduledAllItemsUbiStatsFetcher;
    @MockBean
    private CommonQueryItemsSaleStatsGraphQlClientService commonQueryItemsSaleStatsGraphQlClientService;
    @MockBean
    private ItemSaleUbiStatsService itemSaleUbiStatsService;

    @Test
    public void fetchAllItemSalesUbiStats_should_get_stats_from_graphql_and_save_to_stats_service() {
        List<GroupedItemDaySalesUbiStats> stats = new ArrayList<>();
        when(commonQueryItemsSaleStatsGraphQlClientService.fetchAllItemSalesUbiStats()).thenReturn(stats);

        scheduledAllItemsUbiStatsFetcher.fetchAllItemSalesUbiStats();

        verify(itemSaleUbiStatsService).saveAllItemSaleUbiStats(same(stats));
    }

}