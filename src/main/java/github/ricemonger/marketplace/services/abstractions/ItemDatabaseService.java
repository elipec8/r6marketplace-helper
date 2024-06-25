package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;

import java.util.Collection;

public interface ItemDatabaseService {
    void saveAll(Collection<Item> items);

    Item findById(String itemId);

    Collection<Item> findAll();
}
