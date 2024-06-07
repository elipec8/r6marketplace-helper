package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TagPostgresMapperTest {

    @SpyBean
    private TagPostgresMapper tagPostgresMapper;

    @Test
    void mapEntities_should_map_all_Tag_entities() {
        Collection<Tag> tags = Arrays.asList(
            new Tag("tag1", "value1", TagGroup.Event)
        );

        Collection<TagEntity> mapped = Arrays.asList(
                new TagEntity("tag1", "value1", TagGroup.Event)
        );

        Collection<TagEntity> result = tagPostgresMapper.mapTagEntities(tags);

        assertTrue(entitiesAreEqual(mapped.iterator().next(), result.iterator().next()));

        verify(tagPostgresMapper, times(1)).mapTagEntity(same(tags.iterator().next()));
    }

    @Test
    void mapEntity_should_map_Tag_entity_with_proper_fields() {

        Tag tag = new Tag("tag1", "value1", TagGroup.Event);
        TagEntity entity = new TagEntity("tag1", "value1", TagGroup.Event);

        assertTrue(entitiesAreEqual(entity, tagPostgresMapper.mapTagEntity(tag)));
    }

    private boolean entitiesAreEqual(TagEntity entity1, TagEntity entity2) {
        return entity1.getName().equals(entity2.getName()) &&
                entity1.getValue().equals(entity2.getValue()) &&
                entity1.getTagGroup().equals(entity2.getTagGroup());
    }
}