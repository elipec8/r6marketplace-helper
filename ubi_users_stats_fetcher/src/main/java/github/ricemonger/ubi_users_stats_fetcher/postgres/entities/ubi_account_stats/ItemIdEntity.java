package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Entity
@Table(name = "item")
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
        if (o instanceof ItemIdEntity itemEntity) {
            return Objects.equals(itemId, itemEntity.itemId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        return isEqual(o);
    }
}
