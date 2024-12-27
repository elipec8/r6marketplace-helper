package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.DTOs.common.PotentialTradePriceAndTimeStats;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static github.ricemonger.item_trade_stats_calculator.services.PotentialTradeStatsService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class PotentialTradeStatsServiceTest {
    @SpyBean
    private PotentialTradeStatsService potentialTradeStatsService;
    @MockBean
    private CommonValuesService commonValuesService;

    @Test
    public void getPotentialBuyTradesStatsOfItem_should_return_expected_result() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        assertEquals(0, potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item).size());

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(10);

        item.setPriorityToBuyByMinSellPrice(1L);
        List<PotentialTradeStats> result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(new PotentialTradeStats(10, TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToBuyByMinSellPrice()), result.get(0));

        item.setMinSellPrice(0);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(new PotentialTradeStats(10, TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToBuyByMinSellPrice()), result.get(0));

        item.setMinSellPrice(1);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(new PotentialTradeStats(1, TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToBuyByMinSellPrice()), result.get(0));

        item.setPriceToBuyIn1Hour(2);
        item.setPriorityToBuyIn1Hour(20L);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(2, result.size());
        assertEquals(new PotentialTradeStats(item.getPriceToBuyIn1Hour(), MINUTES_IN_AN_HOUR, item.getPriorityToBuyIn1Hour()), result.get(1));

        item.setPriceToBuyIn6Hours(3);
        item.setPriorityToBuyIn6Hours(30L);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(3, result.size());
        assertEquals(new PotentialTradeStats(item.getPriceToBuyIn6Hours(), MINUTES_IN_6_HOURS, item.getPriorityToBuyIn6Hours()), result.get(2));

        item.setPriceToBuyIn24Hours(4);
        item.setPriorityToBuyIn24Hours(40L);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(4, result.size());
        assertEquals(new PotentialTradeStats(item.getPriceToBuyIn24Hours(), MINUTES_IN_A_DAY, item.getPriorityToBuyIn24Hours()), result.get(3));

        item.setPriceToBuyIn168Hours(5);
        item.setPriorityToBuyIn168Hours(50L);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(5, result.size());
        assertEquals(new PotentialTradeStats(item.getPriceToBuyIn168Hours(), MINUTES_IN_A_WEEK, item.getPriorityToBuyIn168Hours()), result.get(4));

        item.setPriceToBuyIn720Hours(6);
        item.setPriorityToBuyIn720Hours(60L);
        result = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(item);
        assertEquals(6, result.size());
        assertEquals(new PotentialTradeStats(item.getPriceToBuyIn720Hours(), MINUTES_IN_A_MONTH, item.getPriorityToBuyIn720Hours()), result.get(5));
    }

    @Test
    public void getPotentialSellTradesStatsOfItem_should_return_expected_result() {
        Item item = new Item();

        assertEquals(0, potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item).size());

        item.setPriorityToSellByNextFancySellPrice(3L);
        PotentialTradeStats fancyStats = new PotentialTradeStats(1, 2, item.getPriorityToSellByNextFancySellPrice());
        doReturn(new PotentialTradePriceAndTimeStats(fancyStats.getPrice(), fancyStats.getPrognosedTradeSuccessMinutes())).when(potentialTradeStatsService).calculatePriceAndTimeForNextFancySellPriceSale(same(item));

        List<PotentialTradeStats> result = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(fancyStats, result.get(0));

        item.setPriorityToSellByNextFancySellPrice(3L);
        item.setPriorityToSellByMaxBuyPrice(4L);
        item.setMaxBuyPrice(5);
        PotentialTradeStats maxBuyStats = new PotentialTradeStats(item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToSellByMaxBuyPrice());

        result = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);
        assertEquals(2, result.size());
        assertEquals(fancyStats, result.get(0));
        assertEquals(maxBuyStats, result.get(1));

        item.setMaxBuyPrice(null);
        result = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(fancyStats, result.get(0));


        item.setPriorityToSellByMaxBuyPrice(null);
        item.setMaxBuyPrice(5);

        result = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(item);
        assertEquals(1, result.size());
        assertEquals(fancyStats, result.get(0));
    }

    @Test
    public void calculatePotentialSellTradeStatsByMaxBuyPrice_should_return_calculateSellTradeStats_result_with_max_buy_price_and_TRADE_MANAGER_FIXED_RATE_MINUTES() {
        Item item = new Item();
        item.setMaxBuyPrice(1000);

        doReturn(new PotentialTradeStats(1, 2, 3L)).when(potentialTradeStatsService).calculateSellTradeStats(same(item), same(item.getMaxBuyPrice()), eq(TRADE_MANAGER_FIXED_RATE_MINUTES));

        assertEquals(new PotentialTradeStats(1, 2, 3L), potentialTradeStatsService.calculatePotentialSellTradeStatsByMaxBuyPrice(item));
    }

    @Test
    public void calculatePotentialSellTradeStatsByNextFancySellPrice_should_return_calculateSellTradeStats_result_with_next_fancy_sell_price_and_expected_time() {
        Item item = new Item();

        PotentialTradePriceAndTimeStats priceAndTime = new PotentialTradePriceAndTimeStats(1000, 100);
        doReturn(priceAndTime).when(potentialTradeStatsService).calculatePriceAndTimeForNextFancySellPriceSale(same(item));

        doReturn(new PotentialTradeStats(1, 2, 3L)).when(potentialTradeStatsService).calculateSellTradeStats(same(item), eq(priceAndTime.price()), eq(priceAndTime.time()));

        assertEquals(new PotentialTradeStats(1, 2, 3L), potentialTradeStatsService.calculatePotentialSellTradeStatsByNextFancySellPrice(item));
    }

    @Test
    public void calculatePotentialBuyTradeStatsByMinSellPrice_should_return_calculateBuyTradeStats_result() {
        Item item = new Item();
        item.setMinSellPrice(1000);

        doReturn(new PotentialTradeStats(1, 2, 3L)).when(potentialTradeStatsService).calculateBuyTradeStats(same(item), same(item.getMinSellPrice()), eq(TRADE_MANAGER_FIXED_RATE_MINUTES));

        assertEquals(new PotentialTradeStats(1, 2, 3L), potentialTradeStatsService.calculatePotentialBuyTradeStatsByMinSellPrice(item));
    }

    @Test
    public void calculatePotentialBuyTradeStatsForTime_should_return_expected_result() {
        Map<Integer, Integer> priceAndQuantity1 = new HashMap<>();
        priceAndQuantity1.put(500, 250);
        priceAndQuantity1.put(1000, 250);
        priceAndQuantity1.put(1500, 250);
        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId1 = new ItemDaySalesStatsByItemId();
        itemDaySalesStatsByItemId1.setPriceAndQuantity(priceAndQuantity1);

        Map<Integer, Integer> priceAndQuantity2 = new HashMap<>();
        priceAndQuantity2.put(500, 250); //500
        priceAndQuantity2.put(1000, 250); //500
        priceAndQuantity2.put(1500, 250); //500
        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId2 = new ItemDaySalesStatsByItemId();
        itemDaySalesStatsByItemId2.setPriceAndQuantity(priceAndQuantity2);

        Item item = new Item();
        item.setBuyOrdersCount(5);
        item.setMaxBuyPrice(5);

        doReturn(280).when(potentialTradeStatsService).getSameOrHigherPricesBuyOrdersAmount(item, 1000);
        PotentialTradeStats expected = new PotentialTradeStats(1000, MINUTES_IN_AN_HOUR, 1000L);
        doReturn(expected).when(potentialTradeStatsService).calculateBuyTradeStats(item, expected.getPrice(), MINUTES_IN_AN_HOUR);
        assertEquals(expected, potentialTradeStatsService.calculatePotentialBuyTradeStatsForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_AN_HOUR));

        doReturn(281).when(potentialTradeStatsService).getSameOrHigherPricesBuyOrdersAmount(item, 1000);
        expected = new PotentialTradeStats(1500, MINUTES_IN_AN_HOUR, 1000L);
        doReturn(expected).when(potentialTradeStatsService).calculateBuyTradeStats(item, expected.getPrice(), MINUTES_IN_AN_HOUR);
        assertEquals(expected, potentialTradeStatsService.calculatePotentialBuyTradeStatsForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_AN_HOUR));

        doReturn(470).when(potentialTradeStatsService).getSameOrHigherPricesBuyOrdersAmount(item, 500);
        expected = new PotentialTradeStats(500, MINUTES_IN_A_DAY, 1000L);
        doReturn(expected).when(potentialTradeStatsService).calculateBuyTradeStats(item, expected.getPrice(), MINUTES_IN_A_DAY);
        assertEquals(expected, potentialTradeStatsService.calculatePotentialBuyTradeStatsForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_A_DAY));

        doReturn(471).when(potentialTradeStatsService).getSameOrHigherPricesBuyOrdersAmount(item, 500);
        expected = new PotentialTradeStats(1000, MINUTES_IN_A_DAY, 1000L);
        doReturn(expected).when(potentialTradeStatsService).calculateBuyTradeStats(item, expected.getPrice(), MINUTES_IN_A_DAY);
        assertEquals(expected, potentialTradeStatsService.calculatePotentialBuyTradeStatsForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_A_DAY));

        doReturn(0).when(potentialTradeStatsService).getSameOrHigherPricesBuyOrdersAmount(item, 1500);
        expected = new PotentialTradeStats(null, 1, 1000L);
        doReturn(expected).when(potentialTradeStatsService).calculateBuyTradeStats(item, expected.getPrice(), 1);
        assertEquals(expected, potentialTradeStatsService.calculatePotentialBuyTradeStatsForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), 1));
    }

    @Test
    public void calculatePotentialSellTradeStatsForExistingTrade_should_return_expected_result() {
        UbiTrade existingTrade = new UbiTrade();
        existingTrade.setProposedPaymentPrice(1000);

        Item item = new Item();

        doReturn(10000).when(potentialTradeStatsService).getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade);
        doReturn(new PotentialTradeStats(2, 3, 4L)).when(potentialTradeStatsService).calculateSellTradeStats(same(item), eq(1000), eq(10000));

        assertEquals(new PotentialTradeStats(2, 3, 4L), potentialTradeStatsService.calculatePotentialSellTradeStatsForExistingTrade(item, existingTrade));
    }

    @Test
    public void calculatePotentialBuyTradeStatsForExistingTrade_should_return_expected_result() {
        UbiTrade existingTrade = new UbiTrade();
        existingTrade.setProposedPaymentPrice(1000);

        Item item = new Item();

        doReturn(10000).when(potentialTradeStatsService).getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade);
        doReturn(new PotentialTradeStats(2, 3, 4L)).when(potentialTradeStatsService).calculateBuyTradeStats(same(item), eq(1000), eq(10000));

        assertEquals(new PotentialTradeStats(2, 3, 4L), potentialTradeStatsService.calculatePotentialBuyTradeStatsForExistingTrade(item, existingTrade));
    }

    @Test
    public void getExpectedPaymentsSuccessMinutesForExistingTradeOrNull_should_return_expected_result() {
        UbiTrade ubiTrade = new UbiTrade();
        LocalDateTime now = LocalDateTime.now();
        ubiTrade.setLastModifiedAt(now.minusDays(10));

        Integer prognosedTradeSuccessMinutes = 27000;
        doReturn(prognosedTradeSuccessMinutes).when(potentialTradeStatsService).getPrognosedTradeSuccessMinutesByPriceOrNull(any(), any(), any());

        int expectedTradeTime = 12600; //27000 - 1440 x 10

        assertTrue(Math.abs(potentialTradeStatsService.getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(ubiTrade) - expectedTradeTime) < 5);

        doReturn(10).when(potentialTradeStatsService).getPrognosedTradeSuccessMinutesByPriceOrNull(any(), any(), any());
        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, potentialTradeStatsService.getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(ubiTrade));

        doReturn(null).when(potentialTradeStatsService).getPrognosedTradeSuccessMinutesByPriceOrNull(any(), any(), any());
        assertNull(potentialTradeStatsService.getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(ubiTrade));
    }

    @Test
    public void getPrognosedTradeSuccessMinutesByPriceOrNull_should_return_expected_result() {
        Item item = new Item();
        item.setMinSellPrice(1000);
        item.setMaxBuyPrice(1000);
        item.setMonthSalesPerDay(2);
        item.setPriceToBuyIn1Hour(950);
        item.setPriceToBuyIn6Hours(900);
        item.setPriceToBuyIn24Hours(850);
        item.setPriceToBuyIn168Hours(800);
        item.setPriceToBuyIn720Hours(750);

        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1000, TradeCategory.Buy));
        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1001, TradeCategory.Buy));

        assertEquals(MINUTES_IN_AN_HOUR, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 950, TradeCategory.Buy));
        assertEquals(MINUTES_IN_AN_HOUR, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 951, TradeCategory.Buy));

        assertEquals(MINUTES_IN_6_HOURS, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 900, TradeCategory.Buy));
        assertEquals(MINUTES_IN_6_HOURS, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 901, TradeCategory.Buy));

        assertEquals(MINUTES_IN_A_DAY, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 850, TradeCategory.Buy));
        assertEquals(MINUTES_IN_A_DAY, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 851, TradeCategory.Buy));

        assertEquals(MINUTES_IN_A_WEEK, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 800, TradeCategory.Buy));
        assertEquals(MINUTES_IN_A_WEEK, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 801, TradeCategory.Buy));

        assertEquals(MINUTES_IN_A_MONTH, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 750, TradeCategory.Buy));
        assertEquals(MINUTES_IN_A_MONTH, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 751, TradeCategory.Buy));

        assertNull(potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 749, TradeCategory.Buy));

        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1000, TradeCategory.Sell));
        assertEquals(TRADE_MANAGER_FIXED_RATE_MINUTES, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 999, TradeCategory.Sell));

        assertEquals(MINUTES_IN_A_DAY / 2, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1050, TradeCategory.Sell));

        item.setMonthSalesPerDay(0);
        assertEquals(MINUTES_IN_A_DAY, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1050, TradeCategory.Sell));

        item.setMonthSalesPerDay(null);
        assertEquals(MINUTES_IN_A_DAY, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1050, TradeCategory.Sell));

        assertEquals(null, potentialTradeStatsService.getPrognosedTradeSuccessMinutesByPriceOrNull(item, 1100, TradeCategory.Unknown));
    }

    @Test
    public void calculateSellTradeStats_should_return_expected_object_for_profitable_trade() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);

        int price = 1500;
        int minutesToTrade = 432;

        long expectedPriority = 38L * 500L * 50L * 800L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateSellTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateSellTradeStats_should_return_nulls_if_minutes_to_trade_is_null() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);

        int price = 1500;

        assertEquals(new PotentialTradeStats(price, null, null), potentialTradeStatsService.calculateSellTradeStats(item, price, null));
    }

    @Test
    public void calculateSellTradeStats_should_return_expected_object_for_non_profitable_trade() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);

        int price = 500;
        int minutesToTrade = 432;

        long expectedPriority = -1L * 22L * 500L * 50L * 800L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateSellTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateSellTradeStats_should_return_expected_object_for_zero_monthMedianPrice() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(0);

        int price = 500;
        int minutesToTrade = 432;

        long expectedPriority = 22L * 1L * 1L * 800L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateSellTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateSellTradeStats_should_return_min_tradePriority_if_sellPrice_is_not_minimal() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(15_000);

        Item item = new Item();
        item.setMinSellPrice(1000);

        assertEquals(new PotentialTradeStats(1001, 1, Long.MIN_VALUE), potentialTradeStatsService.calculateSellTradeStats(item, 1001, 1));
    }

    @Test
    public void calculateSellTradeStats_should_return_null_tradePriority_for_null_or_negative_price() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(15_000);

        assertEquals(new PotentialTradeStats(null, 1, null), potentialTradeStatsService.calculateSellTradeStats(new Item(), null, 1));
        assertEquals(new PotentialTradeStats(0, 1, null), potentialTradeStatsService.calculateSellTradeStats(new Item(), 0, 1));
    }

    @Test
    public void calculateBuyTradeStats_should_return_expected_object_for_profitable_trade() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);
        item.setMonthSales(100);

        int price = 500;
        int minutesToTrade = 432;

        long expectedPriority = 100_000L / price * 500L * 50L * 100L * 3927L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateBuyTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateBuyTradeStats_should_return_expected_object_for_non_profitable_trade() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);
        item.setMonthSales(100);

        int price = 1500;
        int minutesToTrade = 432;

        long expectedPriority = -1L * 100_000L / price * 500L * 50L * 100L * 3927L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateBuyTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateBuyTradeStats_should_return_expected_object_for_zero_month_sales() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);
        item.setMonthSales(0);

        int price = 1500;
        int minutesToTrade = 432;

        long expectedPriority = 0;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateBuyTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateBuyTradeStats_should_return_expected_object_for_zero_monthMedianPrice() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(0);
        item.setMonthSales(100);

        int price = 500;
        int minutesToTrade = 432;

        long expectedPriority = -100_000L / price * 1L * 1L * 100L * 3927L;

        assertEquals(new PotentialTradeStats(price, minutesToTrade, expectedPriority), potentialTradeStatsService.calculateBuyTradeStats(item, price, minutesToTrade));
    }

    @Test
    public void calculateBuyTradeStats_should_return_nulls_for_null_time() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(100_000);

        Item item = new Item();
        item.setMonthMedianPrice(1000);
        item.setMonthSales(100);

        int price = 500;

        assertEquals(new PotentialTradeStats(price, null, null), potentialTradeStatsService.calculateBuyTradeStats(item, price, null));
    }

    @Test
    public void calculateBuyTradeStats_should_return_null_tradePriority_for_null_or_negative_price() {
        when(commonValuesService.getMaximumMarketplacePrice()).thenReturn(15_000);

        assertEquals(new PotentialTradeStats(null, 1, null), potentialTradeStatsService.calculateBuyTradeStats(new Item(), null, 1));
        assertEquals(new PotentialTradeStats(0, 1, null), potentialTradeStatsService.calculateBuyTradeStats(new Item(), 0, 1));
    }

    @Test
    public void getSameOrHigherPricesBuyOrdersAmount_should_return_expected_result() {
        Item item = new Item();
        item.setBuyOrdersCount(1000);
        item.setMaxBuyPrice(10_000);

        when(commonValuesService.getMinimumPriceByRarity(any())).thenReturn(10);

        assertEquals(1000, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 9));
        assertEquals(1000, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 10));

        assertEquals(300, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 4999));
        assertEquals(300, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 5000));

        assertEquals(150, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 6599));
        assertEquals(150, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 6600));

        assertEquals(50, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 8999));
        assertEquals(50, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 9000));

        assertEquals(10, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 9999));
        assertEquals(10, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 10_000));

        assertEquals(0, potentialTradeStatsService.getSameOrHigherPricesBuyOrdersAmount(item, 10_001));
    }

    @Test
    public void calculatePriceAndTimeForNextFancySellPriceSale_should_return_expected_result() {
        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(10);

        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(1000);
        item.setMonthSalesPerDay(1440);

        int price = 999;
        int minutesToTrade = 1;
        PotentialTradePriceAndTimeStats expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));

        item.setMonthSalesPerDay(1);
        price = 999;
        minutesToTrade = 1440;
        expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));

        item.setMinSellPrice(10);
        item.setMonthSalesPerDay(100);
        item.setSellOrdersCount(10);
        price = 10;
        minutesToTrade = 144;
        expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));

        item.setMinSellPrice(10);
        item.setMonthSalesPerDay(null);
        item.setSellOrdersCount(null);
        price = 10;
        minutesToTrade = 1440;
        expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));

        item.setMinSellPrice(10);
        item.setMonthSalesPerDay(0);
        item.setSellOrdersCount(0);
        price = 10;
        minutesToTrade = 1440;
        expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));

        item.setMinSellPrice(10);
        item.setMonthSalesPerDay(-1);
        item.setSellOrdersCount(-1);
        price = 10;
        minutesToTrade = 1440;
        expected = new PotentialTradePriceAndTimeStats(price, minutesToTrade);
        assertEquals(expected, potentialTradeStatsService.calculatePriceAndTimeForNextFancySellPriceSale(item));
    }

}