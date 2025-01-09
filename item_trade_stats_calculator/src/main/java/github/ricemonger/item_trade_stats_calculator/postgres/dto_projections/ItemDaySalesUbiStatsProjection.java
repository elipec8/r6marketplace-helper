package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsProjection implements ItemDaySalesUbiStatsProjectionI {
    private String itemId;
    private LocalDate date;
    private Integer lowestPrice;
    private Integer averagePrice;
    private Integer highestPrice;
    private Integer itemsCount;
}
