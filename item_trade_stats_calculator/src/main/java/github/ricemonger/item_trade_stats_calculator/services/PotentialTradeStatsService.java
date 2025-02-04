package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.services.calculators.ItemTradeStatsCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PotentialTradeStatsService {

    private final ItemTradeStatsCalculator itemTradeStatsCalculator;

    public Integer calculatePotentialBuyTradePriceForTime(Item item, List<ItemDaySalesStatsByItemId> resultingPerDayStats, Integer minutesToTrade) {
        return itemTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, resultingPerDayStats, minutesToTrade);
    }
}
