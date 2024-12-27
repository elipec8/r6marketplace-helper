package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemHistoryFieldsEntity {
    @Id
    private String itemId;

    private Integer monthAveragePrice;
    private Integer monthMedianPrice;
    private Integer monthMaxPrice;
    private Integer monthMinPrice;
    private Integer monthSalesPerDay;
    private Integer monthSales;

    private Integer dayAveragePrice;
    private Integer dayMedianPrice;
    private Integer dayMaxPrice;
    private Integer dayMinPrice;
    private Integer daySales;

    private Long priorityToSellByMaxBuyPrice; //updated with every item stats update, not recalculation
    private Long priorityToSellByNextFancySellPrice; //updated with every item stats update, not recalculation

    private Long priorityToBuyByMinSellPrice; //updated with every item stats update, not recalculation

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
}
