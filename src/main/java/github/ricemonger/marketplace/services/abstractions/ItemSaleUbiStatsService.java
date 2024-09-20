package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemSaleUbiStats;

import java.util.Collection;

public interface ItemSaleUbiStatsService {
    void saveAll(Collection<ItemSaleUbiStats> statsList);
}
