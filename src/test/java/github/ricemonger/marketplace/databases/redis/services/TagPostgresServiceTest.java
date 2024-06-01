package github.ricemonger.marketplace.databases.redis.services;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.TagPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.utils.dtos.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagPostgresServiceTest {

    @MockBean
    private TagPostgresRepository tagPostgresRepository;

    @SpyBean
    private TagPostgresMapper tagPostgresMapper;

    @Autowired
    private TagPostgresService tagPostgresService;

    @Test
    void saveAll_should_map_and_handle_to_repository() {
        Collection<Tag> tags = new ArrayList<>();
        Collection<TagEntity> entities = new ArrayList<>();

        when(tagPostgresMapper.mapEntities(tags)).thenReturn(entities);

        tagPostgresService.saveAll(tags);

        verify(tagPostgresRepository).saveAll(same(entities));
    }
}