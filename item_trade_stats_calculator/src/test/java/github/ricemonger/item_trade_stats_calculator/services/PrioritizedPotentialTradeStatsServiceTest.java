package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.services.calculators.ItemTradeStatsCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PrioritizedPotentialTradeStatsServiceTest {
    @Autowired
    private PotentialTradeStatsService potentialTradeStatsService;
    @MockBean
    private ItemTradeStatsCalculator itemTradeStatsCalculator;

    @Test
    public void calculatePotentialBuyTradePriceForTime_should_return_calculator_result() {
        Item item = mock(Item.class);
        List<ItemDaySalesStatsByItemId> resultingPerDayStats = mock(List.class);
        Integer minutesToTrade = 0;

        when(itemTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(same(item), same(resultingPerDayStats), same(minutesToTrade))).thenReturn(100);

        Integer result = potentialTradeStatsService.calculatePotentialBuyTradePriceForTime(item, resultingPerDayStats, minutesToTrade);

        assertEquals(100, result);
    }
}