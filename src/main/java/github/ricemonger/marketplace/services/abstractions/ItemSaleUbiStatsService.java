package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;

import java.util.Collection;
import java.util.List;

public interface ItemSaleUbiStatsService {
    void saveAll(Collection<GroupedItemDaySalesUbiStats> statsList);

    List<ItemDaySalesUbiStats> findAll();

    List<ItemDaySalesUbiStats> findAllForLastMonth();
}
