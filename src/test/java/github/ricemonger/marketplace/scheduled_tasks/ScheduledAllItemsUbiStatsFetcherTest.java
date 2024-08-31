package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.ItemStatsService;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
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
    private ItemStatsService itemStatsService;

    @Test
    public void fetchAllItemUbiStats_should_get_stats_from_graphql_and_save_to_stats_service() {
        List<ItemSaleUbiStats> stats = new ArrayList<>();
        when(graphQlClientService.fetchAllItemsUbiStats()).thenReturn(stats);

        scheduledAllItemsUbiStatsFetcher.fetchAllItemUbiStats();

        verify(graphQlClientService).fetchAllItemsUbiStats();
        verify(itemStatsService).saveAllItemsUbiStats(same(stats));
    }
}