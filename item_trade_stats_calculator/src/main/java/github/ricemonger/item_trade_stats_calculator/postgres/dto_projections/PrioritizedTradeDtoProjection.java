package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrioritizedTradeDtoProjection {
    private String tradeId;
    private Integer minutesToTrade;
    private Long tradePriority;
}
