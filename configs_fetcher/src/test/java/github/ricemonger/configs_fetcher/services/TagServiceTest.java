package github.ricemonger.configs_fetcher.services;

import github.ricemonger.configs_fetcher.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.DTOs.common.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;


@SpringBootTest
class TagServiceTest {
    @Autowired
    private TagService tagService;
    @MockBean
    private TagDatabaseService tagDatabaseService;

    @Test
    public void saveAllTags_should_handle_to_service() {
        List<Tag> tags = Mockito.mock(ArrayList.class);

        tagService.saveAllTags(tags);

        verify(tagDatabaseService).saveAll(same(tags));
    }
}