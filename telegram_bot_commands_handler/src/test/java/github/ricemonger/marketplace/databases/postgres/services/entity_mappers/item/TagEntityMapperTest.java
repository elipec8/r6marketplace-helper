package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TagEntityMapperTest {

    @Autowired
    private TagEntityMapper tagEntityMapper;

    @Test
    public void createDTO_should_properly_map_dto() {
        TagEntity tagEntity = new TagEntity("value", "name", TagGroup.Rarity);

        assertEquals(new Tag("value", "name", TagGroup.Rarity), tagEntityMapper.createDTO(tagEntity));
    }

    @Test
    public void createEntity_should_properly_map_entity() {
        Tag tag = new Tag("value", "name", TagGroup.Rarity);

        assertTrue(new TagEntity("value", "name", TagGroup.Rarity).isFullyEqual(tagEntityMapper.createEntity(tag)));
    }
}