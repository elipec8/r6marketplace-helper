package github.ricemonger.item_day_sales_ubi_stats_fetcher.scheduled_tasks;


import github.ricemonger.item_day_sales_ubi_stats_fetcher.services.ItemSaleUbiStatsService;
import github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.CommonQueryItemsSaleStatsGraphQlClientService;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsUbiStatsFetcher {

    private final CommonQueryItemsSaleStatsGraphQlClientService commonQueryItemsSaleStatsGraphQlClientService;

    private final ItemSaleUbiStatsService itemSaleUbiStatsService;

    @Scheduled(fixedRateString = "${app.scheduling.fixedRate}", initialDelayString = "${app.scheduling.initialDelay}")
    public void fetchAllItemSalesUbiStats() {
        List<GroupedItemDaySalesUbiStats> ubiStats = commonQueryItemsSaleStatsGraphQlClientService.fetchAllItemSalesUbiStats();

        itemSaleUbiStatsService.insertAllItemSaleUbiStats(ubiStats);
    }
}
