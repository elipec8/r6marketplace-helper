package github.ricemonger.item_trade_stats_calculator.services.abstractions;

import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;

import java.util.List;

public interface ItemSaleUbiStatsDatabaseService {
    List<ItemDaySalesUbiStats> findAllForLastMonth();
}
