package github.ricemonger.trades_manager.services;

import github.ricemonger.trades_manager.services.abstractions.UserDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserDatabaseService userDatabaseService;

    @Test
    public void getAllManageableUsers_should_return_service_result() {
        List users = Mockito.mock(List.class);

        when(userDatabaseService.getAllManageableUsers()).thenReturn(users);

        assertSame(users, userService.getAllManageableUsers());
    }
}