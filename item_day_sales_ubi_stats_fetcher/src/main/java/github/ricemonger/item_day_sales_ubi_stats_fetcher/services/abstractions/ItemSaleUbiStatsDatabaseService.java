package github.ricemonger.item_day_sales_ubi_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;

import java.util.Collection;

public interface ItemSaleUbiStatsDatabaseService {
    void insertAll(Collection<GroupedItemDaySalesUbiStats> statsList);
}
