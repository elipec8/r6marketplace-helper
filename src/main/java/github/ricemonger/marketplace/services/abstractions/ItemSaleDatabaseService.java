package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;

import java.util.Collection;
import java.util.List;

public interface ItemSaleDatabaseService {
    void saveAll(Collection<Item> items);

    List<ItemSale> findAll();
}
