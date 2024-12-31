package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.utils.DTOs.common.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagPostgresServiceTest {
    @SpyBean
    private TagPostgresService tagService;
    @MockBean
    private TagPostgresPostgresRepository tagRepository;
    @MockBean
    private TagEntityMapper tagEntityMapper;

    @Test
    public void findAllByNames_should_map_and_return_filtered_entities() {
        TagEntity entity1 = new TagEntity();
        TagEntity entity2 = new TagEntity();
        List<TagEntity> entities = List.of(entity1, entity2);
        when(tagRepository.findAllByNames(Set.of("tag1", "tag2"))).thenReturn(entities);

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        List<Tag> tags = List.of(tag1, tag2);
        when(tagEntityMapper.createDTO(same(entity1))).thenReturn(tag1);
        when(tagEntityMapper.createDTO(same(entity2))).thenReturn(tag2);

        List<Tag> result = tagService.findAllByNames(Set.of("tag1", "tag2"));

        assertTrue(result.size() == tags.size() && result.stream().allMatch(res -> tags.stream().anyMatch(tag -> tag == res)));
    }

    @Test
    public void findAll_should_map_and_return_all_entities() {
        TagEntity entity1 = new TagEntity();
        TagEntity entity2 = new TagEntity();
        List<TagEntity> entities = List.of(entity1, entity2);
        when(tagRepository.findAll()).thenReturn(entities);

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        List<Tag> tags = List.of(tag1, tag2);
        when(tagEntityMapper.createDTO(same(entity1))).thenReturn(tag1);
        when(tagEntityMapper.createDTO(same(entity2))).thenReturn(tag2);

        List<Tag> result = tagService.findAll();

        assertTrue(result.size() == tags.size() && result.stream().allMatch(res -> tags.stream().anyMatch(tag -> tag == res)));
    }
}