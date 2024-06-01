package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TagDatabaseService;
import github.ricemonger.utils.dtos.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TagServiceTest {

    @MockBean
    private TagDatabaseService tagDatabaseService;

    @Autowired
    private TagService tagService;

    @Test
    void saveAll_should_handle_to_service() {
        Collection<Tag> tags = new ArrayList<>();

        tagService.saveAll(tags);

        verify(tagDatabaseService).saveAll(same(tags));
    }
}