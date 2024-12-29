package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntityId {
    private ItemIdEntity item;
    private LocalDate date;
}
