package github.ricemonger.item_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;

import java.util.Collection;

public interface ItemDatabaseService {
    void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> items);

    void updateAllItemsMainFieldsExceptTags(Collection<? extends ItemMainFieldsI> items);

    void updateAllItemsMinSellPrice(Collection<? extends ItemMinSellPrice> items);
}
