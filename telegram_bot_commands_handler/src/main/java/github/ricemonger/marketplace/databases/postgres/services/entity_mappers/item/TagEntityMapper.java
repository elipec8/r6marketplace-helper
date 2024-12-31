package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagEntityMapper {

    public Tag createDTO(TagEntity entity) {
        return new Tag(entity.getValue(), entity.getName(), entity.getTagGroup());
    }

    public TagEntity createEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setValue(tag.getValue());
        entity.setName(tag.getName());
        entity.setTagGroup(tag.getTagGroup());
        return entity;
    }
}
