package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;

import java.util.Collection;
import java.util.List;

public interface ItemDatabaseService {
    void saveAll(Collection<? extends ItemEntityDTO> items);

    ItemEntityDTO findById(String itemId) throws ItemDoesntExistException;

    List<ItemEntityDTO> findAll();
}
