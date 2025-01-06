package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Table(name = "item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemHistoryFieldsEntity {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "month_average_price")
    private Integer monthAveragePrice;
    @Column(name = "month_median_price")
    private Integer monthMedianPrice;
    @Column(name = "month_max_price")
    private Integer monthMaxPrice;
    @Column(name = "month_min_price")
    private Integer monthMinPrice;
    @Column(name = "month_sales_per_day")
    private Integer monthSalesPerDay;
    @Column(name = "month_sales")
    private Integer monthSales;

    @Column(name = "day_average_price")
    private Integer dayAveragePrice;
    @Column(name = "day_median_price")
    private Integer dayMedianPrice;
    @Column(name = "day_max_price")
    private Integer dayMaxPrice;
    @Column(name = "day_min_price")
    private Integer dayMinPrice;
    @Column(name = "day_sales")
    private Integer daySales;

    @Column(name = "priority_to_sell_by_max_buy_price")
    private Long priorityToSellByMaxBuyPrice;
    @Column(name = "priority_to_sell_by_next_fancy_sell_price")
    private Long priorityToSellByNextFancySellPrice;

    @Column(name = "priority_to_buy_by_min_sell_price")
    private Long priorityToBuyByMinSellPrice;

    @Column(name = "priority_to_buy_in_1_hour")
    private Long priorityToBuyIn1Hour;
    @Column(name = "priority_to_buy_in_6_hours")
    private Long priorityToBuyIn6Hours;
    @Column(name = "priority_to_buy_in_24_hours")
    private Long priorityToBuyIn24Hours;
    @Column(name = "priority_to_buy_in_168_hours")
    private Long priorityToBuyIn168Hours;
    @Column(name = "priority_to_buy_in_720_hours")
    private Long priorityToBuyIn720Hours;

    @Column(name = "price_to_buy_in_1_hour")
    private Integer priceToBuyIn1Hour;
    @Column(name = "price_to_buy_in_6_hours")
    private Integer priceToBuyIn6Hours;
    @Column(name = "price_to_buy_in_24_hours")
    private Integer priceToBuyIn24Hours;
    @Column(name = "price_to_buy_in_168_hours")
    private Integer priceToBuyIn168Hours;
    @Column(name = "price_to_buy_in_720_hours")
    private Integer priceToBuyIn720Hours;

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemHistoryFieldsEntity itemEntity)) {
            return false;
        }
        return Objects.equals(this.itemId, itemEntity.itemId);
    }
}
