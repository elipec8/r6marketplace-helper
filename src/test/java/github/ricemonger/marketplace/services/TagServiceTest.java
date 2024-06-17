package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.dtos.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagServiceTest {

    @MockBean
    private TagDatabaseService tagDatabaseService;

    @Autowired
    private TagService tagService;

    @Test
    void saveAll_Tags_should_handle_to_service() {
        Collection<Tag> tags = new ArrayList<>();

        tagService.saveAllTags(tags);

        verify(tagDatabaseService).saveAll(same(tags));
    }

    @Test
    void getTagsByNames_should_handle_to_service() {
        Collection<String> tagNames = new ArrayList<>();

        List<Tag> tags = new ArrayList<>();

        when(tagDatabaseService.findAllByNames(tagNames)).thenReturn(tags);

        assertEquals(tags,tagService.getTagsByNames(tagNames));

        verify(tagDatabaseService).findAllByNames(same(tagNames));
    }

    @Test
    void getAllTags_should_handle_to_service() {
        List<Tag> tags = new ArrayList<>();

        when(tagDatabaseService.findAll()).thenReturn(tags);

        assertEquals(tags,tagService.getAllTags());

        verify(tagDatabaseService).findAll();
    }
}