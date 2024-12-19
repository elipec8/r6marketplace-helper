package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.enums.TagGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    @Column(name = "tag_value") // "value" column name conflicts with H2
    private String value;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private TagGroup tagGroup;

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof TagEntity tagEntity) {
            return Objects.equals(value, tagEntity.value) &&
                   Objects.equals(name, tagEntity.name) &&
                   tagGroup == tagEntity.tagGroup;
        }
        return false;
    }
}
