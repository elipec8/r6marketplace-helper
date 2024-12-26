package github.ricemonger.item_stats_fetcher.services.abstractions;

import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;

import java.util.Collection;

public interface ItemDatabaseService {
    void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> items);
}
