package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;

import java.util.Collection;
import java.util.List;

public interface ItemSaleUbiStatsService {
    void saveAll(Collection<GroupedItemDaySalesUbiStats> statsList);

    List<ItemDaySalesUbiStats> findAllForLastMonth();

    List<ItemDaySalesUbiStats> findAll();
}
