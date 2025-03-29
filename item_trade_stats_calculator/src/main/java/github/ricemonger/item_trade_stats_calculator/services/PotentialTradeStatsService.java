package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.services.calculators.ItemPotentialTradeStatsCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PotentialTradeStatsService {

    private final ItemPotentialTradeStatsCalculator itemPotentialTradeStatsCalculator;

    public Integer calculatePotentialBuyTradePriceForTime(Item item, List<ItemDaySalesStatsByItemId> resultingPerDayStats, Integer minutesToTrade) {
        return itemPotentialTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, resultingPerDayStats, minutesToTrade);
    }
}
