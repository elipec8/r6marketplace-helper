package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStatsEntityDTO;

import java.util.Collection;
import java.util.List;

public interface ItemSaleUbiStatsService {
    void saveAll(Collection<GroupedItemDaySalesUbiStats> statsList);

    List<ItemDaySalesUbiStatsEntityDTO> findAll();

    List<ItemDaySalesUbiStatsEntityDTO> findAllForLastMonth();
}
