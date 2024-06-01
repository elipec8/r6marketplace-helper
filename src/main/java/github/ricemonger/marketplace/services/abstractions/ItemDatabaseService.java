package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;

import java.util.Collection;

public interface ItemDatabaseService {

    void saveAllItemsAndItemSales(Collection<Item> items);

    void saveAllItemSaleHistoryStats(Collection<ItemSaleHistory> histories);

    Collection<Item> findAllItems();

    Collection<ItemSale> findAllItemSales();
}
