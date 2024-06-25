package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;

import java.util.Collection;

public interface ItemSaleDatabaseService {
    void saveAll(Collection<Item> items);

    Collection<ItemSale> findAll();
}
