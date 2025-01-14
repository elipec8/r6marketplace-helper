package github.ricemonger.fast_sell_trade_manager.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellTradeProjection {
    private String tradeId;
    private String itemId;
    private Integer price;
    private Integer itemMonthMedianPrice;
    private Long tradePriority;
}
