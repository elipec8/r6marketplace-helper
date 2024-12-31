package github.ricemonger.utilspostgresschema.full_entities.item;

import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.id_entities.item.IdTagEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "full_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends IdTagEntity {
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tag_group")
    private TagGroup tagGroup;
}
