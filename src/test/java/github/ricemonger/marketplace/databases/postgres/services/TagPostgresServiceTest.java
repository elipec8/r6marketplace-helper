package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagPostgresServiceTest {

    @MockBean
    private TagPostgresRepository repository;

    @Autowired
    private TagPostgresService service;

    @Test
    public void saveAll_should_handle_to_repository() {
        Tag tag1 = new Tag("value1", "name1", TagGroup.Unknown);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Unknown);

        Collection<Tag> tags = List.of(tag1, tag2);

        service.saveAll(tags);

        verify(repository).saveAll(argThat((ArgumentMatcher<Iterable<? extends TagEntity>>) argument -> {
            Collection<Tag> tagsArg = new ArrayList<>();
            argument.forEach(tagEntity -> tagsArg.add(tagEntity.toTag()));

            return tagsArg.contains(tag1) && tagsArg.contains(tag2);
        }));
    }

    @Test
    public void findAllByNames_should_handle_to_repository() {
        Tag tag1 = new Tag("value1", "name1", TagGroup.Unknown);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Unknown);

        List<Tag> tags = List.of(tag1, tag2);
        List<TagEntity> entities = List.of(tags.stream().map(TagEntity::new).toArray(TagEntity[]::new));

        List<String> tagNames = List.of("name1", "name2");

        when(repository.findAllByNames(tagNames)).thenReturn(entities);

        Collection<Tag> result = service.findAllByNames(tagNames);

        assertTrue(result.containsAll(tags) && tags.containsAll(result));
    }
}