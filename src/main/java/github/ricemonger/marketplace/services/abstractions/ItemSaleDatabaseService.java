package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.items.ItemSaleEntityDTO;
import github.ricemonger.utils.DTOs.items.SoldItemDetails;

import java.util.Collection;
import java.util.List;

public interface ItemSaleDatabaseService {
    void saveAll(Collection<? extends SoldItemDetails> soldItems);

    List<ItemSaleEntityDTO> findAll();

    List<ItemSaleEntityDTO> findAllForLastMonth();
}
