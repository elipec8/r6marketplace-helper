package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagPostgresRepositoryTest {
    @SpyBean
    TagPostgresRepository tagPostgresRepository;

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
}