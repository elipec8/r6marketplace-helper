package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemHistoryFieldsEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        ItemHistoryFieldsEntity entity1 = new ItemHistoryFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setDayAveragePrice(1);
        entity1.setMonthMedianPrice(2);
        entity1.setMonthMinPrice(3);
        entity1.setMonthMaxPrice(4);
        entity1.setMonthSales(5);
        entity1.setMonthSalesPerDay(6);
        entity1.setDaySales(7);
        entity1.setDayMinPrice(8);
        entity1.setDayMaxPrice(9);
        entity1.setDayAveragePrice(10);
        entity1.setDayMedianPrice(11);
        entity1.setPriceToBuyIn1Hour(12);
        entity1.setPriceToBuyIn6Hours(13);
        entity1.setPriceToBuyIn24Hours(14);
        entity1.setPriceToBuyIn168Hours(15);
        entity1.setPriceToBuyIn720Hours(16);
        entity1.setPriorityToBuyIn1Hour(17L);
        entity1.setPriorityToBuyIn6Hours(18L);
        entity1.setPriorityToBuyIn24Hours(19L);
        entity1.setPriorityToBuyIn168Hours(20L);
        entity1.setPriorityToBuyIn720Hours(21L);
        entity1.setPriorityToBuyByMinSellPrice(22L);
        entity1.setPriorityToSellByMaxBuyPrice(23L);
        entity1.setPriorityToSellByNextFancySellPrice(24L);

        ItemHistoryFieldsEntity entity2 = new ItemHistoryFieldsEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemHistoryFieldsEntity entity = new ItemHistoryFieldsEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemHistoryFieldsEntity entity1 = new ItemHistoryFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setDayAveragePrice(1);
        entity1.setMonthMedianPrice(2);
        entity1.setMonthMinPrice(3);
        entity1.setMonthMaxPrice(4);
        entity1.setMonthSales(5);
        entity1.setMonthSalesPerDay(6);
        entity1.setDaySales(7);
        entity1.setDayMinPrice(8);
        entity1.setDayMaxPrice(9);
        entity1.setDayAveragePrice(10);
        entity1.setDayMedianPrice(11);
        entity1.setPriceToBuyIn1Hour(12);
        entity1.setPriceToBuyIn6Hours(13);
        entity1.setPriceToBuyIn24Hours(14);
        entity1.setPriceToBuyIn168Hours(15);
        entity1.setPriceToBuyIn720Hours(16);
        entity1.setPriorityToBuyIn1Hour(17L);
        entity1.setPriorityToBuyIn6Hours(18L);
        entity1.setPriorityToBuyIn24Hours(19L);
        entity1.setPriorityToBuyIn168Hours(20L);
        entity1.setPriorityToBuyIn720Hours(21L);
        entity1.setPriorityToBuyByMinSellPrice(22L);
        entity1.setPriorityToSellByMaxBuyPrice(23L);
        entity1.setPriorityToSellByNextFancySellPrice(24L);

        ItemHistoryFieldsEntity entity2 = new ItemHistoryFieldsEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemHistoryFieldsEntity entity = new ItemHistoryFieldsEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ItemHistoryFieldsEntity entity1 = new ItemHistoryFieldsEntity();
        entity1.setItemId("itemId1");

        ItemHistoryFieldsEntity entity2 = new ItemHistoryFieldsEntity();
        entity2.setItemId("itemId2");

        assertNotEquals(entity1, entity2);
    }
}