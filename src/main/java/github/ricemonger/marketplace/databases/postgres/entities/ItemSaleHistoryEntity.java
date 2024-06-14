package github.ricemonger.marketplace.databases.postgres.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
}
