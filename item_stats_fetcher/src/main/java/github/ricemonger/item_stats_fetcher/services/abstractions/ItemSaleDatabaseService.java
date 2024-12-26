package github.ricemonger.item_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.ItemSale;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;

import java.util.Collection;
import java.util.List;

public interface ItemSaleDatabaseService {
    void saveAllItemsLastSales(Collection<? extends SoldItemDetails> items);
}
