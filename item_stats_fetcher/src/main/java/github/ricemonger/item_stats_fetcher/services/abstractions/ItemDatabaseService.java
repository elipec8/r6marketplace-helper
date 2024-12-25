package github.ricemonger.item_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;

import java.util.Collection;

public interface ItemDatabaseService {
    void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> items);

    void saveAllItemsLastSales(Collection<? extends SoldItemDetails> items);
}
