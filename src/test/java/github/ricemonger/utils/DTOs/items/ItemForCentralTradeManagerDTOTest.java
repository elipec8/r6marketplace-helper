package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemForCentralTradeManagerDTOTest {

    @Test
    public void tradeByFilterManager_constructor_should_set_all_fields() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setPriority(1);
        tradeByFiltersManager.setTradeOperationType(TradeOperationType.BUY);
        tradeByFiltersManager.setMinProfitPercent(50);
        tradeByFiltersManager.setMinBuySellProfit(30);

        Item item = new Item();
        item.setItemId("itemId");
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

        ItemForCentralTradeManagerDTO itemForTradeDTO = new ItemForCentralTradeManagerDTO(item, tradeByFiltersManager);

        assertEquals(item.getItemId(), itemForTradeDTO.getItemId());
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
        assertEquals(tradeByFiltersManager.getMinProfitPercent(), itemForTradeDTO.getMinProfitPercent());

        assertEquals(0, itemForTradeDTO.getBuyBoundaryPrice());
        assertEquals(Integer.MAX_VALUE, itemForTradeDTO.getSellBoundaryPrice());
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

        ItemForCentralTradeManagerDTO itemForTradeDTO = new ItemForCentralTradeManagerDTO(item, tradeByItemIdManager);

        assertEquals(item.getItemId(), itemForTradeDTO.getItemId());
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
        assertEquals(Integer.MIN_VALUE, itemForTradeDTO.getMinProfitPercent());
    }

    @Test
    public void hashCode_and_equals_should_use_only_itemId_tradeOperationType() {
        ItemForCentralTradeManagerDTO itemForTradeDTO1 = new ItemForCentralTradeManagerDTO();
        itemForTradeDTO1.setItemId("itemId");
        itemForTradeDTO1.setRarity(ItemRarity.RARE);
        itemForTradeDTO1.setMaxBuyPrice(1);
        itemForTradeDTO1.setBuyOrdersCount(2);
        itemForTradeDTO1.setMinSellPrice(3);
        itemForTradeDTO1.setSellOrdersCount(4);
        itemForTradeDTO1.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        itemForTradeDTO1.setLastSoldPrice(5);
        itemForTradeDTO1.setMonthAveragePrice(6);
        itemForTradeDTO1.setMonthMedianPrice(7);
        itemForTradeDTO1.setMonthMaxPrice(8);
        itemForTradeDTO1.setMonthMinPrice(9);
        itemForTradeDTO1.setMonthSalesPerDay(10);
        itemForTradeDTO1.setDayAveragePrice(11);
        itemForTradeDTO1.setDayMedianPrice(12);
        itemForTradeDTO1.setDayMaxPrice(13);
        itemForTradeDTO1.setDayMinPrice(14);
        itemForTradeDTO1.setDaySales(15);
        itemForTradeDTO1.setPriceToSellIn1Hour(16);
        itemForTradeDTO1.setPriceToSellIn6Hours(17);
        itemForTradeDTO1.setPriceToSellIn24Hours(18);
        itemForTradeDTO1.setPriceToSellIn168Hours(19);
        itemForTradeDTO1.setPriceToBuyIn1Hour(20);
        itemForTradeDTO1.setPriceToBuyIn6Hours(21);
        itemForTradeDTO1.setPriceToBuyIn24Hours(22);
        itemForTradeDTO1.setPriceToBuyIn168Hours(23);
        itemForTradeDTO1.setBuyBoundaryPrice(24);
        itemForTradeDTO1.setSellBoundaryPrice(25);
        itemForTradeDTO1.setMinBuySellProfit(26);
        itemForTradeDTO1.setMinProfitPercent(27);
        itemForTradeDTO1.setTradeOperationType(TradeOperationType.BUY);
        itemForTradeDTO1.setPriority(1);

        ItemForCentralTradeManagerDTO itemForTradeDTO2 = new ItemForCentralTradeManagerDTO();
        itemForTradeDTO2.setItemId("itemId");
        itemForTradeDTO2.setRarity(ItemRarity.UNCOMMON);
        itemForTradeDTO2.setMaxBuyPrice(2);
        itemForTradeDTO2.setBuyOrdersCount(3);
        itemForTradeDTO2.setMinSellPrice(4);
        itemForTradeDTO2.setSellOrdersCount(5);
        itemForTradeDTO2.setLastSoldAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        itemForTradeDTO2.setLastSoldPrice(6);
        itemForTradeDTO2.setMonthAveragePrice(7);
        itemForTradeDTO2.setMonthMedianPrice(8);
        itemForTradeDTO2.setMonthMaxPrice(9);
        itemForTradeDTO2.setMonthMinPrice(10);
        itemForTradeDTO2.setMonthSalesPerDay(11);
        itemForTradeDTO2.setDayAveragePrice(12);
        itemForTradeDTO2.setDayMedianPrice(13);
        itemForTradeDTO2.setDayMaxPrice(14);
        itemForTradeDTO2.setDayMinPrice(15);
        itemForTradeDTO2.setDaySales(16);
        itemForTradeDTO2.setPriceToSellIn1Hour(17);
        itemForTradeDTO2.setPriceToSellIn6Hours(18);
        itemForTradeDTO2.setPriceToSellIn24Hours(19);
        itemForTradeDTO2.setPriceToSellIn168Hours(20);
        itemForTradeDTO2.setPriceToBuyIn1Hour(21);
        itemForTradeDTO2.setPriceToBuyIn6Hours(22);
        itemForTradeDTO2.setPriceToBuyIn24Hours(23);
        itemForTradeDTO2.setPriceToBuyIn168Hours(24);
        itemForTradeDTO2.setBuyBoundaryPrice(25);
        itemForTradeDTO2.setSellBoundaryPrice(26);
        itemForTradeDTO2.setMinBuySellProfit(27);
        itemForTradeDTO2.setMinProfitPercent(28);
        itemForTradeDTO2.setTradeOperationType(TradeOperationType.BUY);
        itemForTradeDTO2.setPriority(2);

        assertEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertEquals(itemForTradeDTO1, itemForTradeDTO2);

        itemForTradeDTO2.setTradeOperationType(TradeOperationType.SELL);
        assertNotEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertNotEquals(itemForTradeDTO1, itemForTradeDTO2);

        itemForTradeDTO2.setTradeOperationType(TradeOperationType.BUY);
        itemForTradeDTO2.setItemId("itemId2");
        assertNotEquals(itemForTradeDTO1.hashCode(), itemForTradeDTO2.hashCode());
        assertNotEquals(itemForTradeDTO1, itemForTradeDTO2);
    }

    @Test
    public void equals_should_return_true_for_same_object() {
        ItemForCentralTradeManagerDTO itemForTradeDTO1 = new ItemForCentralTradeManagerDTO();
        ItemForCentralTradeManagerDTO itemForTradeDTO2 = itemForTradeDTO1;
        assertEquals(itemForTradeDTO1, itemForTradeDTO2);
    }

    @Test
    public void equals_should_return_false_for_wrong_class_or_null() {
        assertNotEquals(new ItemForCentralTradeManagerDTO(), null);
        assertNotEquals(new ItemForCentralTradeManagerDTO(), new Object());
    }
}