package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagValueEntity {
    @Id
    @Column(name = "tag_value") // "value" column name conflicts with H2
    private String value;

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof TagValueEntity tagEntity) {
            return Objects.equals(value, tagEntity.value);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        return isEqual(o);
    }
}
