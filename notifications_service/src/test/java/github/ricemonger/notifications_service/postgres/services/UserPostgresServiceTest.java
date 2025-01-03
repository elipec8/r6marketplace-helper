package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private UserPostgresRepository userPostgresRepository;

    @Test
    public void getUserChatId_should_return_repository_result() {
        String chatId = "123";
        when(userPostgresRepository.findTelegramUserChatIdById(1L)).thenReturn(Optional.of(chatId));

        assertEquals(chatId, userPostgresService.getUserChatId(1L));
    }

    @Test
    public void getUserChatId_should_throw_if_user_doesnt_exist() {
        when(userPostgresRepository.findTelegramUserChatIdById(1L)).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> userPostgresService.getUserChatId(1L));
    }
}