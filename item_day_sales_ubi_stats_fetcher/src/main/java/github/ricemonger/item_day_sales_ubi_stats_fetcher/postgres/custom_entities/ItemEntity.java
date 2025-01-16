package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.custom_entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemEntity itemEntity)) {
            return false;
        }
        return Objects.equals(this.itemId, itemEntity.itemId);
    }
}
