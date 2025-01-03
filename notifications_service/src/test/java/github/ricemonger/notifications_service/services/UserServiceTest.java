package github.ricemonger.notifications_service.services;

import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserDatabaseService userDatabaseService;

    @Test
    public void getUserChatId_should_return_service_result() {
        Long userId = 1L;
        String chatId = "123";
        when(userDatabaseService.getUserChatId(userId)).thenReturn(chatId);

        assertEquals(chatId, userService.getUserChatId(userId));
    }
}