package github.ricemonger.marketplace.databases.postgres.entities.item;


import github.ricemonger.utils.dtos.ItemSaleHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "item_sale_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleHistoryEntity {
    @Id
    private String itemId;

    private int monthAveragePrice;
    private int monthMedianPrice;

    private int monthMaxPrice;
    private int monthMinPrice;

    private int monthSalesPerDay;

    private int dayAveragePrice;
    private int dayMedianPrice;

    private int dayMaxPrice;
    private int dayMinPrice;

    private int daySales;

    private int expectedProfitByCurrentPrices;
    private int expectedProfitPercentByCurrentPrices;

    private int hoursToSellFor120;
    private int lastOrdersCountCurrentPriceOrLastSoldWasHigherThan120;
    private Date lastDateCurrentPriceOrLastSoldWasHigherThan120;

    private int priceToSellIn1Hour;
    private int priceToSellIn6Hours;
    private int priceToSellIn24Hours;
    private int priceToSellIn168Hours;

    private int priceToBuyIn1Hour;
    private int priceToBuyIn6Hours;
    private int priceToBuyIn24Hours;
    private int priceToBuyIn168Hours;

    public ItemSaleHistoryEntity(ItemSaleHistory history) {
        this.itemId = history.getItemId();

        this.monthAveragePrice = history.getMonthAveragePrice();
        this.monthMedianPrice = history.getMonthMedianPrice();
        this.monthMaxPrice = history.getMonthMaxPrice();
        this.monthMinPrice = history.getMonthMinPrice();
        this.monthSalesPerDay = history.getMonthSalesPerDay();

        this.dayAveragePrice = history.getDayAveragePrice();
        this.dayMedianPrice = history.getDayMedianPrice();
        this.dayMaxPrice = history.getDayMaxPrice();
        this.dayMinPrice = history.getDayMinPrice();
        this.daySales = history.getDaySales();

        this.expectedProfitByCurrentPrices = history.getExpectedProfitByCurrentPrices();
        this.expectedProfitPercentByCurrentPrices = history.getExpectedProfitPercentByCurrentPrices();

        this.hoursToSellFor120 = history.getHoursToSellFor120();
        this.lastOrdersCountCurrentPriceOrLastSoldWasHigherThan120 = history.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120();
        this.lastDateCurrentPriceOrLastSoldWasHigherThan120 = history.getLastDateCurrentPriceOrLastSoldWasHigherThan120();

        this.priceToSellIn1Hour = history.getPriceToSellIn1Hour();
        this.priceToSellIn6Hours = history.getPriceToSellIn6Hours();
        this.priceToSellIn24Hours = history.getPriceToSellIn24Hours();
        this.priceToSellIn168Hours = history.getPriceToSellIn168Hours();

        this.priceToBuyIn1Hour = history.getPriceToBuyIn1Hour();
        this.priceToBuyIn6Hours = history.getPriceToBuyIn6Hours();
        this.priceToBuyIn24Hours = history.getPriceToBuyIn24Hours();
        this.priceToBuyIn168Hours = history.getPriceToBuyIn168Hours();
    }

    public ItemSaleHistory toItemSaleHistory() {
        ItemSaleHistory history = new ItemSaleHistory();
        history.setItemId(this.itemId);

        history.setMonthAveragePrice(this.monthAveragePrice);
        history.setMonthMedianPrice(this.monthMedianPrice);
        history.setMonthMaxPrice(this.monthMaxPrice);
        history.setMonthMinPrice(this.monthMinPrice);
        history.setMonthSalesPerDay(this.monthSalesPerDay);

        history.setDayAveragePrice(this.dayAveragePrice);
        history.setDayMedianPrice(this.dayMedianPrice);
        history.setDayMaxPrice(this.dayMaxPrice);
        history.setDayMinPrice(this.dayMinPrice);
        history.setDaySales(this.daySales);

        history.setExpectedProfitByCurrentPrices(this.expectedProfitByCurrentPrices);
        history.setExpectedProfitPercentByCurrentPrices(this.expectedProfitPercentByCurrentPrices);

        history.setHoursToSellFor120(this.hoursToSellFor120);
        history.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(this.lastOrdersCountCurrentPriceOrLastSoldWasHigherThan120);
        history.setLastDateCurrentPriceOrLastSoldWasHigherThan120(this.lastDateCurrentPriceOrLastSoldWasHigherThan120);

        history.setPriceToSellIn1Hour(this.priceToSellIn1Hour);
        history.setPriceToSellIn6Hours(this.priceToSellIn6Hours);
        history.setPriceToSellIn24Hours(this.priceToSellIn24Hours);
        history.setPriceToSellIn168Hours(this.priceToSellIn168Hours);

        history.setPriceToBuyIn1Hour(this.priceToBuyIn1Hour);
        history.setPriceToBuyIn6Hours(this.priceToBuyIn6Hours);
        history.setPriceToBuyIn24Hours(this.priceToBuyIn24Hours);
        history.setPriceToBuyIn168Hours(this.priceToBuyIn168Hours);
        return history;
    }
}
