package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.MINUTES_IN_AN_HOUR;
import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.MINUTES_IN_A_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemPotentialTradeStatsCalculatorTest {

    private final ItemFancyPriceCalculator itemFancyPriceCalculator = mock(ItemFancyPriceCalculator.class);

    private final ItemTradeTimeCalculator itemTradeTimeCalculator = mock(ItemTradeTimeCalculator.class);

    private final ItemPotentialTradeStatsCalculator itemPotentialTradeStatsCalculator = spy(new ItemPotentialTradeStatsCalculator(itemFancyPriceCalculator, itemTradeTimeCalculator));

    @Test
    public void calculatePotentialBuyTradePriceForTime_should_return_expected_result() {
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

        doReturn(280).when(itemTradeTimeCalculator).getSameOrHigherPricesBuyOrdersAmount(item, 1000);
        Integer expected = 1000;
        when(itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, expected)).thenReturn(expected - 1);
        assertEquals(expected - 1, itemPotentialTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_AN_HOUR));

        doReturn(281).when(itemTradeTimeCalculator).getSameOrHigherPricesBuyOrdersAmount(item, 1000);
        expected = 1500;
        when(itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, expected)).thenReturn(expected - 1);
        assertEquals(expected - 1, itemPotentialTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_AN_HOUR));

        doReturn(470).when(itemTradeTimeCalculator).getSameOrHigherPricesBuyOrdersAmount(item, 500);
        expected = 500;
        when(itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, expected)).thenReturn(expected - 1);
        assertEquals(expected - 1, itemPotentialTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_A_DAY));

        doReturn(471).when(itemTradeTimeCalculator).getSameOrHigherPricesBuyOrdersAmount(item, 500);
        expected = 1000;
        when(itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, expected)).thenReturn(expected - 1);
        assertEquals(expected - 1, itemPotentialTradeStatsCalculator.calculatePotentialBuyTradePriceForTime(item, List.of(itemDaySalesStatsByItemId1, itemDaySalesStatsByItemId2), MINUTES_IN_A_DAY));
    }
}