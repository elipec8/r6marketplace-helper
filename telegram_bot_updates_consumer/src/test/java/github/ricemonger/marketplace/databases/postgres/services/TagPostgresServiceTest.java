package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utilspostgresschema.full_entities.item.TagEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagPostgresServiceTest {
    @SpyBean
    private TagPostgresService tagService;
    @MockBean
    private TagPostgresRepository tagRepository;
    @MockBean
    private TagEntityMapper tagEntityMapper;

    @Test
    public void findAllByNames_should_map_and_return_filtered_entities() {
        TagEntity entity1 = Mockito.mock(TagEntity.class);
        TagEntity entity2 = Mockito.mock(TagEntity.class);
        List<TagEntity> entities = List.of(entity1, entity2);
        when(tagRepository.findAllByNames(Set.of("tag1", "tag2"))).thenReturn(entities);

        Tag tag1 = Mockito.mock(Tag.class);
        Tag tag2 = Mockito.mock(Tag.class);
        List<Tag> tags = List.of(tag1, tag2);
        when(tagEntityMapper.createDTO(same(entity1))).thenReturn(tag1);
        when(tagEntityMapper.createDTO(same(entity2))).thenReturn(tag2);

        List<Tag> result = tagService.findAllByNames(Set.of("tag1", "tag2"));

        assertTrue(result.size() == tags.size() && result.stream().allMatch(res -> tags.stream().anyMatch(tag -> tag == res)));
    }

    @Test
    public void findAllByTagGroup_should_map_and_return_all_entities() {
        TagEntity entity1 = Mockito.mock(TagEntity.class);
        TagEntity entity2 = Mockito.mock(TagEntity.class);
        List<TagEntity> entities = List.of(entity1, entity2);

        TagGroup tagGroup = TagGroup.Season;
        when(tagRepository.findAllByTagGroup(tagGroup)).thenReturn(entities);

        Tag tag1 = Mockito.mock(Tag.class);
        Tag tag2 = Mockito.mock(Tag.class);
        List<Tag> tags = List.of(tag1, tag2);
        when(tagEntityMapper.createDTO(same(entity1))).thenReturn(tag1);
        when(tagEntityMapper.createDTO(same(entity2))).thenReturn(tag2);

        List<Tag> result = tagService.findAllByTagGroup(tagGroup);

        assertTrue(result.size() == tags.size() && result.stream().allMatch(res -> tags.stream().anyMatch(tag -> tag == res)));
    }
}