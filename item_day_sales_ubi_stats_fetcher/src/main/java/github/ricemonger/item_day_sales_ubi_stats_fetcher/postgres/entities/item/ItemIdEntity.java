package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemIdEntity {
    @Id
    private String itemId;

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemIdEntity itemIdEntity) {
            return Objects.equals(itemId, itemIdEntity.itemId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        return isEqual(o);
    }
}
