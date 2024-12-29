package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleEntityId implements Serializable {
    private ItemIdEntity item;
    private LocalDateTime soldAt;
}
