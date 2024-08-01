package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TagPostgresServiceTest {
    @Autowired
    private TagPostgresService tagService;
    @Autowired
    private TagPostgresRepository tagRepository;

    @BeforeEach
    void setUp() {
        tagRepository.deleteAll();
    }

    @Test
    public void saveAll_should_create_new_tags_if_doesnt_exist() {
        Tag tag1 = new Tag("value1", "name", TagGroup.Unknown);
        Tag tag2 = new Tag("value2", "name", TagGroup.Unknown);

        tagService.saveAll(Set.of(tag1, tag2));

        assertEquals(2, tagRepository.count());
    }

    @Test
    public void saveAll_should_update_tag_if_exists() {
        Tag tag1 = new Tag("value1", "name", TagGroup.Unknown);
        tagService.saveAll(Set.of(tag1));

        tag1.setName("name name");
        tag1.setTagGroup(TagGroup.Season);
        tagService.saveAll(Set.of(tag1));

        assertEquals(1, tagRepository.count());
        assertEquals("name name", tagRepository.findAll().get(0).getName());
        assertEquals(TagGroup.Season, tagRepository.findAll().get(0).getTagGroup());
    }

    @Test
    public void findAllByNames_should_return_tags_by_names() {
        Tag tag1 = new Tag("value1", "name1", TagGroup.Unknown);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Unknown);

        tagRepository.saveAll(Set.of(new TagEntity(tag1), new TagEntity(tag2)));

        assertEquals(0, tagService.findAllByNames(Set.of("new name", "name")).size());
        assertEquals(1, tagService.findAllByNames(Set.of("name1")).size());
        assertEquals(2, tagService.findAllByNames(Set.of("name1", "name2")).size());
    }

    @Test
    public void findAll_should_return_all_tags() {
        Tag tag1 = new Tag("value1", "name1", TagGroup.Unknown);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Unknown);

        tagRepository.saveAll(Set.of(new TagEntity(tag1), new TagEntity(tag2)));

        assertEquals(2, tagService.findAll().size());
    }
}