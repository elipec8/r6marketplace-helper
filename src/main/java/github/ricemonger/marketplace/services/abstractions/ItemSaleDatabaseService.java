package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;

import java.util.Collection;

public interface ItemSaleDatabaseService {
    Collection<ItemSale> findAllItemSales();

    void saveAllItemSales(Collection<Item> items);
}
