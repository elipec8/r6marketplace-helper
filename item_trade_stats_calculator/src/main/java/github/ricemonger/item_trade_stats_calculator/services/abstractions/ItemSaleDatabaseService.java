package github.ricemonger.item_trade_stats_calculator.services.abstractions;

import github.ricemonger.utils.DTOs.common.ItemSale;

import java.util.List;

public interface ItemSaleDatabaseService {
    List<ItemSale> findAllForLastMonth();
}
