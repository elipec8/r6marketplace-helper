package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemHistoryFields;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemSaleHistoryEntityTest {

    @Test
    public void toItemSaleHistoryEntity_should_properly_map_with_valid_fields() {
        ItemHistoryFields history = new ItemHistoryFields();
        history.setItemId("1");
        history.setMonthAveragePrice(1);
        history.setMonthMedianPrice(2);
        history.setMonthMaxPrice(3);
        history.setMonthMinPrice(4);
        history.setMonthSalesPerDay(5);
        history.setDayAveragePrice(6);
        history.setDayMedianPrice(7);
        history.setDayMaxPrice(8);
        history.setDayMinPrice(9);
        history.setDaySales(10);
        history.setExpectedProfitByCurrentPrices(11);
        history.setExpectedProfitPercentByCurrentPrices(12);
        history.setHoursToSellFor120(13);
        history.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(14);
        history.setLastDateCurrentPriceOrLastSoldWasHigherThan120(new Date(0));
        history.setPriceToSellIn1Hour(16);
        history.setPriceToSellIn6Hours(17);
        history.setPriceToSellIn24Hours(18);
        history.setPriceToSellIn168Hours(19);
        history.setPriceToBuyIn1Hour(20);
        history.setPriceToBuyIn6Hours(21);
        history.setPriceToBuyIn24Hours(22);
        history.setPriceToBuyIn168Hours(23);

        ItemSaleHistoryEntity expected = new ItemSaleHistoryEntity();
        expected.setItemId("1");
        expected.setMonthAveragePrice(1);
        expected.setMonthMedianPrice(2);
        expected.setMonthMaxPrice(3);
        expected.setMonthMinPrice(4);
        expected.setMonthSalesPerDay(5);
        expected.setDayAveragePrice(6);
        expected.setDayMedianPrice(7);
        expected.setTodayMaxPrice(8);
        expected.setTodayMinPrice(9);
        expected.setTodaySales(10);
        expected.setExpectedProfitByCurrentPrices(11);
        expected.setExpectedProfitPercentByCurrentPrices(12);
        expected.setHoursToSellFor120(13);
        expected.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(14);
        expected.setLastDateCurrentPriceOrLastSoldWasHigherThan120(new Date(0));
        expected.setPriceToSellIn1Hour(16);
        expected.setPriceToSellIn6Hours(17);
        expected.setPriceToSellIn24Hours(18);
        expected.setPriceToSellIn168Hours(19);
        expected.setPriceToBuyIn1Hour(20);
        expected.setPriceToBuyIn6Hours(21);
        expected.setPriceToBuyIn24Hours(22);
        expected.setPriceToBuyIn168Hours(23);

        ItemSaleHistoryEntity actual = new ItemSaleHistoryEntity(history);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSaleHistoryEntity_should_properly_map_with_null_fields() {
        ItemHistoryFields history = new ItemHistoryFields();
        history.setItemId(null);
        history.setLastDateCurrentPriceOrLastSoldWasHigherThan120(null);

        ItemSaleHistoryEntity expected = new ItemSaleHistoryEntity();
        expected.setItemId(null);
        expected.setMonthAveragePrice(0);
        expected.setMonthMedianPrice(0);
        expected.setMonthMaxPrice(0);
        expected.setMonthMinPrice(0);
        expected.setMonthSalesPerDay(0);
        expected.setDayAveragePrice(0);
        expected.setDayMedianPrice(0);
        expected.setTodayMaxPrice(0);
        expected.setTodayMinPrice(0);
        expected.setTodaySales(0);
        expected.setExpectedProfitByCurrentPrices(0);
        expected.setExpectedProfitPercentByCurrentPrices(0);
        expected.setHoursToSellFor120(0);
        expected.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(0);
        expected.setLastDateCurrentPriceOrLastSoldWasHigherThan120(null);
        expected.setPriceToSellIn1Hour(0);
        expected.setPriceToSellIn6Hours(0);
        expected.setPriceToSellIn24Hours(0);
        expected.setPriceToSellIn168Hours(0);
        expected.setPriceToBuyIn1Hour(0);
        expected.setPriceToBuyIn6Hours(0);
        expected.setPriceToBuyIn24Hours(0);
        expected.setPriceToBuyIn168Hours(0);

        ItemSaleHistoryEntity actual = new ItemSaleHistoryEntity(history);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSaleHistory_should_properly_map_with_valid_fields() {
        ItemSaleHistoryEntity entity = new ItemSaleHistoryEntity();
        entity.setItemId("1");
        entity.setMonthAveragePrice(1);
        entity.setMonthMedianPrice(2);
        entity.setMonthMaxPrice(3);
        entity.setMonthMinPrice(4);
        entity.setMonthSalesPerDay(5);
        entity.setDayAveragePrice(6);
        entity.setDayMedianPrice(7);
        entity.setTodayMaxPrice(8);
        entity.setTodayMinPrice(9);
        entity.setTodaySales(10);
        entity.setExpectedProfitByCurrentPrices(11);
        entity.setExpectedProfitPercentByCurrentPrices(12);
        entity.setHoursToSellFor120(13);
        entity.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(14);
        entity.setLastDateCurrentPriceOrLastSoldWasHigherThan120(new Date(0));
        entity.setPriceToSellIn1Hour(16);
        entity.setPriceToSellIn6Hours(17);
        entity.setPriceToSellIn24Hours(18);
        entity.setPriceToSellIn168Hours(19);
        entity.setPriceToBuyIn1Hour(20);
        entity.setPriceToBuyIn6Hours(21);
        entity.setPriceToBuyIn24Hours(22);
        entity.setPriceToBuyIn168Hours(23);

        ItemHistoryFields expected = new ItemHistoryFields();
        expected.setItemId("1");
        expected.setMonthAveragePrice(1);
        expected.setMonthMedianPrice(2);
        expected.setMonthMaxPrice(3);
        expected.setMonthMinPrice(4);
        expected.setMonthSalesPerDay(5);
        expected.setDayAveragePrice(6);
        expected.setDayMedianPrice(7);
        expected.setDayMaxPrice(8);
        expected.setDayMinPrice(9);
        expected.setDaySales(10);
        expected.setExpectedProfitByCurrentPrices(11);
        expected.setExpectedProfitPercentByCurrentPrices(12);
        expected.setHoursToSellFor120(13);
        expected.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(14);
        expected.setLastDateCurrentPriceOrLastSoldWasHigherThan120(new Date(0));
        expected.setPriceToSellIn1Hour(16);
        expected.setPriceToSellIn6Hours(17);
        expected.setPriceToSellIn24Hours(18);
        expected.setPriceToSellIn168Hours(19);
        expected.setPriceToBuyIn1Hour(20);
        expected.setPriceToBuyIn6Hours(21);
        expected.setPriceToBuyIn24Hours(22);
        expected.setPriceToBuyIn168Hours(23);

        ItemHistoryFields actual = entity.toItemSaleHistory();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemSaleHistory_should_properly_map_with_null_fields() {
        ItemSaleHistoryEntity entity = new ItemSaleHistoryEntity();
        entity.setItemId(null);
        entity.setLastDateCurrentPriceOrLastSoldWasHigherThan120(null);

        ItemHistoryFields expected = new ItemHistoryFields();
        expected.setItemId(null);
        expected.setMonthAveragePrice(0);
        expected.setMonthMedianPrice(0);
        expected.setMonthMaxPrice(0);
        expected.setMonthMinPrice(0);
        expected.setMonthSalesPerDay(0);
        expected.setDayAveragePrice(0);
        expected.setDayMedianPrice(0);
        expected.setDayMaxPrice(0);
        expected.setDayMinPrice(0);
        expected.setDaySales(0);
        expected.setExpectedProfitByCurrentPrices(0);
        expected.setExpectedProfitPercentByCurrentPrices(0);
        expected.setHoursToSellFor120(0);
        expected.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(0);
        expected.setLastDateCurrentPriceOrLastSoldWasHigherThan120(null);
        expected.setPriceToSellIn1Hour(0);
        expected.setPriceToSellIn6Hours(0);
        expected.setPriceToSellIn24Hours(0);
        expected.setPriceToSellIn168Hours(0);
        expected.setPriceToBuyIn1Hour(0);
        expected.setPriceToBuyIn6Hours(0);
        expected.setPriceToBuyIn24Hours(0);
        expected.setPriceToBuyIn168Hours(0);

        ItemHistoryFields actual = entity.toItemSaleHistory();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemSaleHistoryEntity entity1, ItemSaleHistoryEntity entity2) {
        return Objects.equals(entity1.getItemId(), entity2.getItemId()) &&
               entity1.getMonthAveragePrice() == entity2.getMonthAveragePrice() &&
               entity1.getMonthMedianPrice() == entity2.getMonthMedianPrice() &&
               entity1.getMonthMaxPrice() == entity2.getMonthMaxPrice() &&
               entity1.getMonthMinPrice() == entity2.getMonthMinPrice() &&
               entity1.getMonthSalesPerDay() == entity2.getMonthSalesPerDay() &&
               entity1.getDayAveragePrice() == entity2.getDayAveragePrice() &&
               entity1.getDayMedianPrice() == entity2.getDayMedianPrice() &&
               entity1.getTodayMaxPrice() == entity2.getTodayMaxPrice() &&
               entity1.getTodayMinPrice() == entity2.getTodayMinPrice() &&
               entity1.getTodaySales() == entity2.getTodaySales() &&
               entity1.getExpectedProfitByCurrentPrices() == entity2.getExpectedProfitByCurrentPrices() &&
               entity1.getExpectedProfitPercentByCurrentPrices() == entity2.getExpectedProfitPercentByCurrentPrices() &&
               entity1.getHoursToSellFor120() == entity2.getHoursToSellFor120() &&
               entity1.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() == entity2.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() &&
               Objects.equals(entity1.getLastDateCurrentPriceOrLastSoldWasHigherThan120(), entity2.getLastDateCurrentPriceOrLastSoldWasHigherThan120()) &&
               entity1.getPriceToSellIn1Hour() == entity2.getPriceToSellIn1Hour() &&
               entity1.getPriceToSellIn6Hours() == entity2.getPriceToSellIn6Hours() &&
               entity1.getPriceToSellIn24Hours() == entity2.getPriceToSellIn24Hours() &&
               entity1.getPriceToSellIn168Hours() == entity2.getPriceToSellIn168Hours() &&
               entity1.getPriceToBuyIn1Hour() == entity2.getPriceToBuyIn1Hour() &&
               entity1.getPriceToBuyIn6Hours() == entity2.getPriceToBuyIn6Hours() &&
               entity1.getPriceToBuyIn24Hours() == entity2.getPriceToBuyIn24Hours() &&
               entity1.getPriceToBuyIn168Hours() == entity2.getPriceToBuyIn168Hours();
    }
}