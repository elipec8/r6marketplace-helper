package github.ricemonger.configs_fetcher.database.postgres.services.entity_mappers.item;

import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagEntityMapper {

    public TagEntity createEntity(Tag tag) {
        return new TagEntity(tag.getValue(), tag.getName(), tag.getTagGroup());
    }
}
