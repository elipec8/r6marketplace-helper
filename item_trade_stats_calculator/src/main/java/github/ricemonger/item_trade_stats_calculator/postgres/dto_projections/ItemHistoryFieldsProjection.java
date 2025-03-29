package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemHistoryFieldsProjection {
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

    private Integer priceToBuyIn1Hour;
    private Integer priceToBuyIn6Hours;
    private Integer priceToBuyIn24Hours;
    private Integer priceToBuyIn168Hours;
    private Integer priceToBuyIn720Hours;
}
