package github.ricemonger.item_day_sales_ubi_stats_fetcher.services;


import github.ricemonger.item_day_sales_ubi_stats_fetcher.services.abstractions.ItemSaleUbiStatsDatabaseService;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemSaleUbiStatsService {

    private final ItemSaleUbiStatsDatabaseService itemSaleUbiStatsDatabaseService;

    public void saveAllItemSaleUbiStats(List<GroupedItemDaySalesUbiStats> ubiStats) {
        itemSaleUbiStatsDatabaseService.saveAll(ubiStats);
    }
}
