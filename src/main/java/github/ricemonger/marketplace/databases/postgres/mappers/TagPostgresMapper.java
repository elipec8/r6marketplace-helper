package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.utils.dtos.Tag;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagPostgresMapper {

    public Collection<TagEntity> mapTagEntities(Collection<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return List.of();
        }
        else {
            return tags.stream().map(this::mapTagEntity).collect(Collectors.toList());
        }
    }

    public TagEntity mapTagEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setValue(tag.getValue());
        entity.setName(tag.getName());
        entity.setTagGroup(tag.getTagGroup());
        return entity;
    }

    public Collection<Tag> mapTags(Collection<TagEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        else {
            return entities.stream().map(this::mapTag).collect(Collectors.toList());
        }
    }

    public Tag mapTag(TagEntity tagEntity) {
        return new Tag(tagEntity.getValue(), tagEntity.getName(), tagEntity.getTagGroup());
    }
}
