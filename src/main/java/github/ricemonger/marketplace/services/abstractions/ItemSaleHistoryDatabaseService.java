package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.ItemSaleHistory;

import java.util.Collection;

public interface ItemSaleHistoryDatabaseService {
    void saveAllItemSaleHistoryStats(Collection<ItemSaleHistory> histories);
}
