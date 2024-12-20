package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.UserDatabaseService;
import github.ricemonger.utils.DTOs.personal.UserForCentralTradeManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserDatabaseService userDatabaseService;

    @Test
    public void getAllManageableUsers_should_return_service_result() {
        List expected = List.of(new UserForCentralTradeManager());
        when(userDatabaseService.getAllManageableUsers()).thenReturn(expected);

        assertSame(expected, userService.getAllManageableUsers());
    }

    @Test
    public void getAllManageableUsers_should_throw_if_service_throws() {
        when(userDatabaseService.getAllManageableUsers()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getAllManageableUsers());
    }
}