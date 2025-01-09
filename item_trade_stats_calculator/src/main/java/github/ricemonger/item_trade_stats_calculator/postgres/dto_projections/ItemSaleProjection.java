package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleProjection implements ItemSaleProjectionI {
    private String itemId;
    private LocalDateTime soldAt;
    private Integer price;
}
