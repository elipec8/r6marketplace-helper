package github.ricemonger.configs_fetcher.database.postgres.services.entity_mappers.item;

import github.ricemonger.configs_fetcher.database.postgres.entities.TagEntity;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TagEntityMapperTest {
    @Autowired
    private TagEntityMapper tagEntityMapper;

    @Test
    public void createEntity_should_properly_map_entity() {
        Tag tag = new Tag("value", "name", TagGroup.Rarity);

        assertTrue(new TagEntity("value", "name", TagGroup.Rarity).isFullyEqual(tagEntityMapper.createEntity(tag)));
    }
}