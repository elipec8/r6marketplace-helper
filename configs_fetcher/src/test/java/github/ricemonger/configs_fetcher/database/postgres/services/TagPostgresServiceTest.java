package github.ricemonger.configs_fetcher.database.postgres.services;

import github.ricemonger.configs_fetcher.database.postgres.entities.TagEntity;
import github.ricemonger.configs_fetcher.database.postgres.repositories.TagPostgresRepository;
import github.ricemonger.configs_fetcher.database.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.utils.DTOs.common.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagPostgresServiceTest {
    @Autowired
    private TagPostgresService tagPostgresService;
    @MockBean
    private TagPostgresRepository tagRepository;
    @MockBean
    private TagEntityMapper tagEntityMapper;

    @Test
    public void saveAll_should_map_and_save_all_dtos() {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        List<Tag> tags = List.of(tag1, tag2);
        TagEntity tagEntity1 = new TagEntity();
        TagEntity tagEntity2 = new TagEntity();
        List<TagEntity> entities = List.of(tagEntity1, tagEntity2);

        when(tagEntityMapper.createEntity(same(tag1))).thenReturn(tagEntity1);
        when(tagEntityMapper.createEntity(same(tag2))).thenReturn(tagEntity2);

        tagPostgresService.saveAll(tags);

        verify(tagRepository).saveAll(argThat((a -> StreamSupport.stream(a.spliterator(), false).toList().size() == entities.size() && StreamSupport.stream(a.spliterator(), false).toList().stream().allMatch(ar -> entities.stream().anyMatch(en -> en.isFullyEqual(ar))))));
    }
}