package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.utils.dtos.Tag;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TagPostgresMapper {

    public Collection<TagEntity> mapEntities(Collection<Tag> tags) {
        return tags.stream().map(this::mapEntity).collect(Collectors.toList());
    }

    public TagEntity mapEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setValue(tag.getValue());
        entity.setName(tag.getName());
        entity.setTagGroup(tag.getTagGroup());
        return entity;
    }
}
