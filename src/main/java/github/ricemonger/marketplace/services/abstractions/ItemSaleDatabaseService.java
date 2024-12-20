package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemSale;
import github.ricemonger.utils.DTOs.items.SoldItemDetails;

import java.util.Collection;
import java.util.List;

public interface ItemSaleDatabaseService {
    void saveAll(Collection<? extends SoldItemDetails> soldItems);

    List<ItemSale> findAllForLastMonth();

    List<ItemSale> findAll();
}
