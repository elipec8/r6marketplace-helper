package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntityId {

    private ItemIdEntity item;

    private LocalDate date;

    public ItemDaySalesUbiStatsEntityId(String itemId, LocalDate date) {
        this.item = new ItemIdEntity(itemId);
        this.date = date;
    }

    public String getItemId_() {
        return item.getItemId();
    }

    public int hashCode() {
        return Objects.hash(item.getItemId(), date);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemDaySalesUbiStatsEntityId itemDaySalesUbiStatsEntityId)) {
            return false;
        }
        boolean item = this.item == itemDaySalesUbiStatsEntityId.item ||
                       (this.item != null &&
                        itemDaySalesUbiStatsEntityId.item != null &&
                        Objects.equals(this.item.getItemId(), itemDaySalesUbiStatsEntityId.item.getItemId()));

        boolean date = Objects.equals(this.date, itemDaySalesUbiStatsEntityId.date);

        return item && date;
    }
}
