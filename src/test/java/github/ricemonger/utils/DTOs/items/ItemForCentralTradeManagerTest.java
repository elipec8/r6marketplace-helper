package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemForCentralTradeManagerTest {

    @Test
    public void tradeByFilterManager_constructor_should_set_all_fields() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setPriority(1);
        tradeByFiltersManager.setTradeOperationType(TradeOperationType.BUY);
        tradeByFiltersManager.setMinProfitPercent(50);
        tradeByFiltersManager.setMinBuySellProfit(30);

        Item item = new Item();
        item.setItemId("itemId");
        item.setName("name");
        item.setRarity(ItemRarity.RARE);
        item.setMaxBuyPrice(1);
        item.setBuyOrdersCount(2);
        item.setMinSellPrice(3);
        item.setSellOrdersCount(4);
        item.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        item.setLastSoldPrice(5);
        item.setMonthAveragePrice(6);
        item.setMonthMedianPrice(7);
        item.setMonthMaxPrice(8);
        item.setMonthMinPrice(9);
        item.setMonthSalesPerDay(10);
        item.setDayAveragePrice(11);
        item.setDayMedianPrice(12);
        item.setDayMaxPrice(13);
        item.setDayMinPrice(14);
        item.setDaySales(15);
        item.setPriceToSellIn1Hour(16);
        item.setPriceToSellIn6Hours(17);
        item.setPriceToSellIn24Hours(18);
        item.setPriceToSellIn168Hours(19);
        item.setPriceToBuyIn1Hour(20);
        item.setPriceToBuyIn6Hours(21);
        item.setPriceToBuyIn24Hours(22);
        item.setPriceToBuyIn168Hours(23);

        ItemForCentralTradeManager itemForTradeDTO = new ItemForCentralTradeManager(item, tradeByFiltersManager);

        assertEquals(item.getItemId(), itemForTradeDTO.getItemId());
        assertEquals(item.getName(), itemForTradeDTO.getName());
        assertEquals(item.getRarity(), itemForTradeDTO.getRarity());
        assertEquals(item.getMaxBuyPrice(), itemForTradeDTO.getMaxBuyPrice());
        assertEquals(item.getBuyOrdersCount(), itemForTradeDTO.getBuyOrdersCount());
        assertEquals(item.getMinSellPrice(), itemForTradeDTO.getMinSellPrice());
        assertEquals(item.getSellOrdersCount(), itemForTradeDTO.getSellOrdersCount());
        assertEquals(item.getLastSoldAt(), itemForTradeDTO.getLastSoldAt());
        assertEquals(item.getLastSoldPrice(), itemForTradeDTO.getLastSoldPrice());
        assertEquals(item.getMonthAveragePrice(), itemForTradeDTO.getMonthAveragePrice());
        assertEquals(item.getMonthMedianPrice(), itemForTradeDTO.getMonthMedianPrice());
        assertEquals(item.getMonthMaxPrice(), itemForTradeDTO.getMonthMaxPrice());
        assertEquals(item.getMonthMinPrice(), itemForTradeDTO.getMonthMinPrice());
        assertEquals(item.getMonthSalesPerDay(), itemForTradeDTO.getMonthSalesPerDay());
        assertEquals(item.getDayAveragePrice(), itemForTradeDTO.getDayAveragePrice());
        assertEquals(item.getDayMedianPrice(), itemForTradeDTO.getDayMedianPrice());
        assertEquals(item.getDayMaxPrice(), itemForTradeDTO.getDayMaxPrice());
        assertEquals(item.getDayMinPrice(), itemForTradeDTO.getDayMinPrice());
        assertEquals(item.getDaySales(), itemForTradeDTO.getDaySales());
        assertEquals(item.getPriceToSellIn1Hour(), itemForTradeDTO.getPriceToSellIn1Hour());
        assertEquals(item.getPriceToSellIn6Hours(), itemForTradeDTO.getPriceToSellIn6Hours());
        assertEquals(item.getPriceToSellIn24Hours(), itemForTradeDTO.getPriceToSellIn24Hours());
        assertEquals(item.getPriceToSellIn168Hours(), itemForTradeDTO.getPriceToSellIn168Hours());
        assertEquals(item.getPriceToBuyIn1Hour(), itemForTradeDTO.getPriceToBuyIn1Hour());
        assertEquals(item.getPriceToBuyIn6Hours(), itemForTradeDTO.getPriceToBuyIn6Hours());
        assertEquals(item.getPriceToBuyIn24Hours(), itemForTradeDTO.getPriceToBuyIn24Hours());
        assertEquals(item.getPriceToBuyIn168Hours(), itemForTradeDTO.getPriceToBuyIn168Hours());

        assertEquals(tradeByFiltersManager.getTradeOperationType(), itemForTradeDTO.getTradeOperationType());
        assertEquals(tradeByFiltersManager.getPriority(), itemForTradeDTO.getPriority());
        assertEquals(tradeByFiltersManager.getMinBuySellProfit(), itemForTradeDTO.getMinBuySellProfit());
        assertEquals(tradeByFiltersManager.getMinProfitPercent(), itemForTradeDTO.getMinBuySellProfitPercent());

        assertEquals(0, itemForTradeDTO.getSellBoundaryPrice());
        assertEquals(Integer.MAX_VALUE, itemForTradeDTO.getBuyBoundaryPrice());
    }

    @Test
    public void tradeByItemIdManager_constructor_should_set_all_fields() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setPriority(1);
        tradeByItemIdManager.setTradeOperationType(TradeOperationType.BUY);
        tradeByItemIdManager.setBuyBoundaryPrice(60);
        tradeByItemIdManager.setSellBoundaryPrice(70);

        Item item = new Item();
        item.setItemId("itemId");
        item.setName("name");
        item.setRarity(ItemRarity.RARE);
        item.setMaxBuyPrice(1);
        item.setBuyOrdersCount(2);
        item.setMinSellPrice(3);
        item.setSellOrdersCount(4);
        item.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        item.setLastSoldPrice(5);
        item.setMonthAveragePrice(6);
        item.setMonthMedianPrice(7);
        item.setMonthMaxPrice(8);
        item.setMonthMinPrice(9);
        item.setMonthSalesPerDay(10);
        item.setDayAveragePrice(11);
        item.setDayMedianPrice(12);
        item.setDayMaxPrice(13);
        item.setDayMinPrice(14);
        item.setDaySales(15);
        item.setPriceToSellIn1Hour(16);
        item.setPriceToSellIn6Hours(17);
        item.setPriceToSellIn24Hours(18);
        item.setPriceToSellIn168Hours(19);
        item.setPriceToBuyIn1Hour(20);
        item.setPriceToBuyIn6Hours(21);
        item.setPriceToBuyIn24Hours(22);
        item.setPriceToBuyIn168Hours(23);

        ItemForCentralTradeManager itemForTradeDTO = new ItemForCentralTradeManager(item, tradeByItemIdManager);

        assertEquals(item.getItemId(), itemForTradeDTO.getItemId());
        assertEquals(item.getName(), itemForTradeDTO.getName());
        assertEquals(item.getRarity(), itemForTradeDTO.getRarity());
        assertEquals(item.getMaxBuyPrice(), itemForTradeDTO.getMaxBuyPrice());
        assertEquals(item.getBuyOrdersCount(), itemForTradeDTO.getBuyOrdersCount());
        assertEquals(item.getMinSellPrice(), itemForTradeDTO.getMinSellPrice());
        assertEquals(item.getSellOrdersCount(), itemForTradeDTO.getSellOrdersCount());
        assertEquals(item.getLastSoldAt(), itemForTradeDTO.getLastSoldAt());
        assertEquals(item.getLastSoldPrice(), itemForTradeDTO.getLastSoldPrice());
        assertEquals(item.getMonthAveragePrice(), itemForTradeDTO.getMonthAveragePrice());
        assertEquals(item.getMonthMedianPrice(), itemForTradeDTO.getMonthMedianPrice());
        assertEquals(item.getMonthMaxPrice(), itemForTradeDTO.getMonthMaxPrice());
        assertEquals(item.getMonthMinPrice(), itemForTradeDTO.getMonthMinPrice());
        assertEquals(item.getMonthSalesPerDay(), itemForTradeDTO.getMonthSalesPerDay());
        assertEquals(item.getDayAveragePrice(), itemForTradeDTO.getDayAveragePrice());
        assertEquals(item.getDayMedianPrice(), itemForTradeDTO.getDayMedianPrice());
        assertEquals(item.getDayMaxPrice(), itemForTradeDTO.getDayMaxPrice());
        assertEquals(item.getDayMinPrice(), itemForTradeDTO.getDayMinPrice());
        assertEquals(item.getDaySales(), itemForTradeDTO.getDaySales());
        assertEquals(item.getPriceToSellIn1Hour(), itemForTradeDTO.getPriceToSellIn1Hour());
        assertEquals(item.getPriceToSellIn6Hours(), itemForTradeDTO.getPriceToSellIn6Hours());
        assertEquals(item.getPriceToSellIn24Hours(), itemForTradeDTO.getPriceToSellIn24Hours());
        assertEquals(item.getPriceToSellIn168Hours(), itemForTradeDTO.getPriceToSellIn168Hours());
        assertEquals(item.getPriceToBuyIn1Hour(), itemForTradeDTO.getPriceToBuyIn1Hour());
        assertEquals(item.getPriceToBuyIn6Hours(), itemForTradeDTO.getPriceToBuyIn6Hours());
        assertEquals(item.getPriceToBuyIn24Hours(), itemForTradeDTO.getPriceToBuyIn24Hours());
        assertEquals(item.getPriceToBuyIn168Hours(), itemForTradeDTO.getPriceToBuyIn168Hours());

        assertEquals(tradeByItemIdManager.getTradeOperationType(), itemForTradeDTO.getTradeOperationType());
        assertEquals(tradeByItemIdManager.getPriority(), itemForTradeDTO.getPriority());
        assertEquals(tradeByItemIdManager.getBuyBoundaryPrice(), itemForTradeDTO.getBuyBoundaryPrice());
        assertEquals(tradeByItemIdManager.getSellBoundaryPrice(), itemForTradeDTO.getSellBoundaryPrice());

        assertEquals(Integer.MIN_VALUE, itemForTradeDTO.getMinBuySellProfit());
        assertEquals(Integer.MIN_VALUE, itemForTradeDTO.getMinBuySellProfitPercent());
    }

    @Test
    public void hashCode_and_equals_should_use_only_itemId_tradeOperationType() {
        ItemForCentralTradeManager itemForTradeDTO1 = new ItemForCentralTradeManager();

        Item item1 = new Item();
        item1.setItemId("itemId");
        item1.setName("name");
        item1.setRarity(ItemRarity.RARE);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        item1.setLastSoldPrice(5);
        item1.setMonthAveragePrice(6);
        item1.setMonthMedianPrice(7);
        item1.setMonthMaxPrice(8);
        item1.setMonthMinPrice(9);
        item1.setMonthSalesPerDay(10);
        item1.setDayAveragePrice(11);
        item1.setDayMedianPrice(12);
        item1.setDayMaxPrice(13);
        item1.setDayMinPrice(14);
        item1.setDaySales(15);
        item1.setPriceToSellIn1Hour(16);
        item1.setPriceToSellIn6Hours(17);
        item1.setPriceToSellIn24Hours(18);
        item1.setPriceToSellIn168Hours(19);
        item1.setPriceToBuyIn1Hour(20);
        item1.setPriceToBuyIn6Hours(21);
        item1.setPriceToBuyIn24Hours(22);
        item1.setPriceToBuyIn168Hours(23);

        itemForTradeDTO1.setItem(item1);
        itemForTradeDTO1.setBuyBoundaryPrice(24);
        itemForTradeDTO1.setSellBoundaryPrice(25);
        itemForTradeDTO1.setMinBuySellProfit(26);
        itemForTradeDTO1.setMinBuySellProfitPercent(27);
        itemForTradeDTO1.setTradeOperationType(TradeOperationType.BUY);
        itemForTradeDTO1.setPriority(1);

        ItemForCentralTradeManager itemForTradeDTO2 = new ItemForCentralTradeManager();

        Item item2 = new Item();
        item2.setItemId("itemId");
        item2.setName("name1");
        item2.setRarity(ItemRarity.UNCOMMON);
        item2.setMaxBuyPrice(2);
        item2.setBuyOrdersCount(3);
        item2.setMinSellPrice(4);
        item2.setSellOrdersCount(5);
        item2.setLastSoldAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        item2.setLastSoldPrice(6);
        item2.setMonthAveragePrice(7);
        item2.setMonthMedianPrice(8);
        item2.setMonthMaxPrice(9);
        item2.setMonthMinPrice(10);
        item2.setMonthSalesPerDay(11);
        item2.setDayAveragePrice(12);
        item2.setDayMedianPrice(13);
        item2.setDayMaxPrice(14);
        item2.setDayMinPrice(15);
        item2.setDaySales(16);
        item2.setPriceToSellIn1Hour(17);
        item2.setPriceToSellIn6Hours(18);
        item2.setPriceToSellIn24Hours(19);
        item2.setPriceToSellIn168Hours(20);
        item2.setPriceToBuyIn1Hour(21);
        item2.setPriceToBuyIn6Hours(22);
        item2.setPriceToBuyIn24Hours(23);
        item2.setPriceToBuyIn168Hours(24);


        itemForTradeDTO2.setItem(item2);
        itemForTradeDTO2.setBuyBoundaryPrice(25);
        itemForTradeDTO2.setSellBoundaryPrice(26);
        itemForTradeDTO2.setMinBuySellProfit(27);
        itemForTradeDTO2.setMinBuySellProfitPercent(28);
        itemForTradeDTO2.setTradeOperationType(TradeOperationType.BUY);
        itemForTradeDTO2.setPriority(2);

        assertEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertEquals(itemForTradeDTO1, itemForTradeDTO2);

        itemForTradeDTO2.setTradeOperationType(TradeOperationType.SELL);
        assertNotEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertNotEquals(itemForTradeDTO1, itemForTradeDTO2);

        itemForTradeDTO2.setTradeOperationType(TradeOperationType.BUY);
        item2.setItemId("itemId2");
        itemForTradeDTO2.setItem(item2);
        assertNotEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertNotEquals(itemForTradeDTO1, itemForTradeDTO2);
    }

    @Test
    public void equals_should_return_true_for_same_object() {
        ItemForCentralTradeManager itemForTradeDTO1 = new ItemForCentralTradeManager();
        ItemForCentralTradeManager itemForTradeDTO2 = itemForTradeDTO1;
        assertEquals(itemForTradeDTO1, itemForTradeDTO2);
    }

    @Test
    public void equals_should_return_false_for_wrong_class_or_null() {
        assertNotEquals(new ItemForCentralTradeManager(), null);
        assertNotEquals(new ItemForCentralTradeManager(), new Object());
    }
}