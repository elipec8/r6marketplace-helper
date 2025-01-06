package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Table(name = "item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemIdEntity {
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
        if (!(o instanceof ItemIdEntity itemEntity)) {
            return false;
        }
        return Objects.equals(this.itemId, itemEntity.itemId);
    }
}
