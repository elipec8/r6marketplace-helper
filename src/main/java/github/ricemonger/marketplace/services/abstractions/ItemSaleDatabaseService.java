package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.items.ItemSale;

import java.util.Collection;
import java.util.List;

public interface ItemSaleDatabaseService {
    void saveAll(Collection<? extends ItemMainFieldsI> itemMainFields);

    List<ItemSale> findAllSales();

    List<ItemSale> findAllLastMonthSales();
}
