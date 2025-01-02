package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class TagServiceTest {
    @Autowired
    private TagService tagService;
    @MockBean
    private TagDatabaseService tagDatabaseService;

    @Test
    void getTagsByNames_should_handle_to_service() {
        List tagNames = Mockito.mock(List.class);

        List tags = Mockito.mock(List.class);

        when(tagDatabaseService.findAllByNames(same(tagNames))).thenReturn(tags);

        assertSame(tags, tagService.getTagsByNames(tagNames));
    }

    @Test
    void getAllTagsByTagGroup_should_handle_to_service() {
        List tags = Mockito.mock(List.class);

        TagGroup tagGroup = TagGroup.Rarity;

        when(tagDatabaseService.findAllByTagGroup(tagGroup)).thenReturn(tags);

        assertSame(tags, tagService.getAllTagsByTagGroup(tagGroup));
    }
}