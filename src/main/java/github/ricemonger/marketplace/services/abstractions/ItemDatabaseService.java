package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;

import java.util.Collection;
import java.util.List;

public interface ItemDatabaseService {
    void saveAll(Collection<Item> items);

    Item findById(String itemId) throws ItemDoesntExistException;

    List<Item> findAll();
}
