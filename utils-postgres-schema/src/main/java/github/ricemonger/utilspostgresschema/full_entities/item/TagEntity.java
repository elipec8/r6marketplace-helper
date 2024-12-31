package github.ricemonger.utilspostgresschema.full_entities.item;

import github.ricemonger.utils.enums.TagGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "tag")
@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tag_group")
    private TagGroup tagGroup;
    @Id
    @Column(name = "tag_value")
    private String value;

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagEntity tagEntity)) return false;
        return Objects.equals(value, tagEntity.value);
    }
}
