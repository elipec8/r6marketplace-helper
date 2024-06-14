package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;

import java.util.Collection;

public interface ItemDatabaseService {
    void saveAllItems(Collection<Item> items);

    Collection<Item> findAllItems();
}
