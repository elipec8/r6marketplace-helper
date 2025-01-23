package github.ricemonger.fetching_acounts_manager.services;

import github.ricemonger.fetching_acounts_manager.services.abstractions.AuthorizedUbiUsersDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AuthorizedUbiUsersServiceTest {
    @Autowired
    private AuthorizedUbiUsersService authorizedUbiUsersService;
    @MockBean
    private AuthorizedUbiUsersDatabaseService authorizedUbiUsersDatabaseService;

    @Test
    public void saveAuthorizedUbiUsersAndRemoveOthers_should_handle_to_db_service() {
        List list = mock(List.class);

        authorizedUbiUsersService.saveAuthorizedUbiUsersAndRemoveOthers(list);

        verify(authorizedUbiUsersDatabaseService).saveAuthorizedUbiUsersAndRemoveOthers(list);
    }
}