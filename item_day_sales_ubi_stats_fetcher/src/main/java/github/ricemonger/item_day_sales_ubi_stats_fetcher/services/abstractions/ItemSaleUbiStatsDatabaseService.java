package github.ricemonger.item_day_sales_ubi_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;

import java.util.Collection;
import java.util.List;

public interface ItemSaleUbiStatsDatabaseService {
    void saveAll(Collection<GroupedItemDaySalesUbiStats> statsList);
}
