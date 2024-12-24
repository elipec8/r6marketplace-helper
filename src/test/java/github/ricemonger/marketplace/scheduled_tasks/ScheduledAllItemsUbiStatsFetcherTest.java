package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.ItemService;
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
    private GraphQlClientService graphQlClientService;
    @MockBean
    private ItemService itemService;

    @Test
    public void fetchAllItemSalesUbiStats_should_get_stats_from_graphql_and_save_to_stats_service() {
        List<GroupedItemDaySalesUbiStats> stats = new ArrayList<>();
        when(graphQlClientService.fetchAllItemSalesUbiStats()).thenReturn(stats);

        scheduledAllItemsUbiStatsFetcher.fetchAllItemSalesUbiStats();

        verify(itemService).saveAllItemSaleUbiStats(same(stats));
    }
}