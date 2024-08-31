package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.UserDatabaseService;
import github.ricemonger.utils.dtos.TradingUser;
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
    public void getAllTradingUsers_should_return_service_result() {
        List expected = List.of(new TradingUser());
        when(userDatabaseService.getAllTradingUsers()).thenReturn(expected);

        assertSame(expected, userService.getAllTradingUsers());
    }

    @Test
    public void getAllTradingUsers_should_throw_if_service_throws() {
        when(userDatabaseService.getAllTradingUsers()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.getAllTradingUsers());
    }
}