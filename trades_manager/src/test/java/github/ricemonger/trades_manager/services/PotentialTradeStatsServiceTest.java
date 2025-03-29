package github.ricemonger.trades_manager.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.TRADE_MANAGER_FIXED_RATE_MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class PotentialTradeStatsServiceTest {
    @SpyBean
    private PotentialTradeStatsService potentialTradeStatsService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ItemTradeTimeCalculator itemTradeTimeCalculator;

    @Test
    public void getPotentialSellTradesStatsOfItem_should_return_expected_result() {
        Item item = new Item();
        item.setMaxBuyPrice(null);

        PotentialTradeStats nextFancySellPriceTradeStats = new PotentialTradeStats(101, 102);

        when(itemTradeTimeCalculator.calculatePriceAndTimeForNextFancySellPriceSale(same(item))).thenReturn(nextFancySellPriceTradeStats);

        List<PotentialTradeStats> nullMaxBuyPriceTradeStats = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);

        assertEquals(1, nullMaxBuyPriceTradeStats.size());
        assertEquals(nextFancySellPriceTradeStats, nullMaxBuyPriceTradeStats.get(0));

        item.setMaxBuyPrice(103);

        List<PotentialTradeStats> notNullMaxBuyPriceTradeStats = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);

        assertEquals(2, notNullMaxBuyPriceTradeStats.size());
        assertTrue(notNullMaxBuyPriceTradeStats.contains(nextFancySellPriceTradeStats));
        assertTrue(notNullMaxBuyPriceTradeStats.contains(new PotentialTradeStats(103, TRADE_MANAGER_FIXED_RATE_MINUTES)));
    }

    @Test
    public void getPotentialBuyTradesStatsOfItem_should_return_expected_result() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        List<PotentialTradeStats> allNullPricesTradeStats = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);

        assertEquals(1, allNullPricesTradeStats.size());
        assertEquals(new PotentialTradeStats(100, TRADE_MANAGER_FIXED_RATE_MINUTES), allNullPricesTradeStats.get(0));

        item.setMinSellPrice(101);

        List<PotentialTradeStats> notNullMinSellPriceTradeStats = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);

        assertEquals(1, notNullMinSellPriceTradeStats.size());
        assertEquals(new PotentialTradeStats(101, TRADE_MANAGER_FIXED_RATE_MINUTES), notNullMinSellPriceTradeStats.get(0));

        item.setPriceToBuyIn1Hour(102);
        item.setPriceToBuyIn6Hours(103);

        List<PotentialTradeStats> notNullPrices1And6HoursTradeStats = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);

        assertEquals(3, notNullPrices1And6HoursTradeStats.size());
        assertTrue(notNullPrices1And6HoursTradeStats.contains(new PotentialTradeStats(101, TRADE_MANAGER_FIXED_RATE_MINUTES)));
        assertTrue(notNullPrices1And6HoursTradeStats.contains(new PotentialTradeStats(102, ItemTradeTimeCalculator.MINUTES_IN_AN_HOUR)));
        assertTrue(notNullPrices1And6HoursTradeStats.contains(new PotentialTradeStats(103, ItemTradeTimeCalculator.MINUTES_IN_6_HOURS)));

        item.setPriceToBuyIn24Hours(104);
        item.setPriceToBuyIn168Hours(105);
        item.setPriceToBuyIn720Hours(106);

        List<PotentialTradeStats> notNullAllPricesTradeStats = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);

        assertEquals(6, notNullAllPricesTradeStats.size());
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(101, TRADE_MANAGER_FIXED_RATE_MINUTES)));
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(102, ItemTradeTimeCalculator.MINUTES_IN_AN_HOUR)));
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(103, ItemTradeTimeCalculator.MINUTES_IN_6_HOURS)));
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(104, ItemTradeTimeCalculator.MINUTES_IN_A_DAY)));
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(105, ItemTradeTimeCalculator.MINUTES_IN_A_WEEK)));
        assertTrue(notNullAllPricesTradeStats.contains(new PotentialTradeStats(106, ItemTradeTimeCalculator.MINUTES_IN_A_MONTH)));
    }

    @Test
    public void calculateExpectedPaymentsSuccessMinutesForExistingTradeOrNull_should_return_expected_result() {
        UbiTrade ubTrade = new UbiTrade();

        when(itemTradeTimeCalculator.calculateExpectedPaymentsSuccessMinutesForExistingTradeOrNull(same(ubTrade))).thenReturn(100);

        assertEquals(100, potentialTradeStatsService.calculateExpectedPaymentsSuccessMinutesForExistingTradeOrNull(ubTrade));
    }
}