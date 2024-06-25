package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.TagGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    private String value;
    private String name;
    private TagGroup tagGroup;

    public TagEntity(Tag tag) {
        this.value = tag.getValue();
        this.name = tag.getName();
        this.tagGroup = tag.getTagGroup();
    }

    public Tag toTag() {
        Tag tag = new Tag();
        tag.setValue(this.value);
        tag.setName(this.name);
        tag.setTagGroup(this.tagGroup);
        return tag;
    }
}
