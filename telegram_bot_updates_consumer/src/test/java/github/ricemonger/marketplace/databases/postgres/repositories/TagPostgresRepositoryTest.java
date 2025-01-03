package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TagPostgresRepositoryTest {
    @SpyBean
    TagPostgresRepository tagPostgresRepository;

    @BeforeEach
    public void setUp() {
        tagPostgresRepository.deleteAll();
    }

    @Test
    public void findAllByNames_should_return_all_tags_with_given_names() {
        TagEntity tag1 = new TagEntity("value1", "tag1", TagGroup.Rarity);
        TagEntity tag2 = new TagEntity("value2", "tag2", TagGroup.Season);
        TagEntity tag3 = new TagEntity("value3", "tag3", TagGroup.Unknown);
        tagPostgresRepository.save(tag1);
        tagPostgresRepository.save(tag2);
        tagPostgresRepository.save(tag3);

        List<TagEntity> tags = tagPostgresRepository.findAllByNames(List.of("tag1", "tag2"));

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
        tagPostgresRepository.save(tag1);
        tagPostgresRepository.save(tag2);
        tagPostgresRepository.save(tag3);
        tagPostgresRepository.save(tag4);

        List<TagEntity> tags = tagPostgresRepository.findAllByTagGroup(TagGroup.Rarity);

        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag1")));
        assertTrue(tags.stream().anyMatch(tag -> tag.getName().equals("tag4")));
    }
}