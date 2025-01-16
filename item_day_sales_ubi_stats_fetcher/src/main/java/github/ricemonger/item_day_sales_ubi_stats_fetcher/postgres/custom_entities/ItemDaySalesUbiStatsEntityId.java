package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.custom_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntityId {
    private ItemEntity item;
    private LocalDate date;
}
