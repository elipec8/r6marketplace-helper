package github.ricemonger.trades_manager.postgres.services.entity_mappers.item;

import github.ricemonger.trades_manager.postgres.entities.items.TagEntity;
import github.ricemonger.utils.DTOs.common.Tag;
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
}
