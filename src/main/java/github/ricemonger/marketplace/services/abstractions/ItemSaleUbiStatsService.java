package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemSalesUbiStatsByItemId;

import java.util.Collection;
import java.util.List;

public interface ItemSaleUbiStatsService {
    void saveAllSales(Collection<ItemSalesUbiStatsByItemId> statsList);

    List<ItemDaySalesUbiStats> findAllDaySales();

    List<ItemDaySalesUbiStats> findAllLastMonthSales();
}
