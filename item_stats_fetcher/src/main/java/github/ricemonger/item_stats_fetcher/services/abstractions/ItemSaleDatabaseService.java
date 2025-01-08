package github.ricemonger.item_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.SoldItemDetails;

import java.util.Collection;

public interface ItemSaleDatabaseService {
    void insertAllItemsLastSales(Collection<? extends SoldItemDetails> items);
}
