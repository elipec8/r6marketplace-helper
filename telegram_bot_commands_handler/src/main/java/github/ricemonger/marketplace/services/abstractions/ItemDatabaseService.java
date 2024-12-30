package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;

import java.util.List;

public interface ItemDatabaseService {
    Item findById(String itemId) throws ItemDoesntExistException;

    List<Item> findAll();
}
