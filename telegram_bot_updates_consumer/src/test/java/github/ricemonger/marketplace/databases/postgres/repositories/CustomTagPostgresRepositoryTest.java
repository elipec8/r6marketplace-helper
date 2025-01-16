package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomTagPostgresRepositoryTest {
    @SpyBean
    CustomTagPostgresRepository customTagPostgresRepository;

    @BeforeEach
    public void setUp() {
        customTagPostgresRepository.deleteAll();
    }

    @Test
    public void findAllByNames_should_return_all_tags_with_given_names() {
        TagEntity tag1 = new TagEntity("value1", "tag1", TagGroup.Rarity);
        TagEntity tag2 = new TagEntity("value2", "tag2", TagGroup.Season);
        TagEntity tag3 = new TagEntity("value3", "tag3", TagGroup.Unknown);
        customTagPostgresRepository.save(tag1);
        customTagPostgresRepository.save(tag2);
        customTagPostgresRepository.save(tag3);

        List<TagEntity> tags = customTagPostgresRepository.findAllByNames(List.of("tag1", "tag2"));

        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag2")));
    }

    @Test
    public void findAllByTagGroup_should_return_all_tags_with_Given_tagGroup() {
        TagEntity tag1 = new TagEntity("value1", "tag1", TagGroup.Rarity);
        TagEntity tag2 = new TagEntity("value2", "tag2", TagGroup.Season);
        TagEntity tag3 = new TagEntity("value3", "tag3", TagGroup.Unknown);
        TagEntity tag4 = new TagEntity("value4", "tag4", TagGroup.Rarity);
        customTagPostgresRepository.save(tag1);
        customTagPostgresRepository.save(tag2);
        customTagPostgresRepository.save(tag3);
        customTagPostgresRepository.save(tag4);

        List<TagEntity> tags = customTagPostgresRepository.findAllByTagGroup(TagGroup.Rarity);

        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag4")));
    }
}