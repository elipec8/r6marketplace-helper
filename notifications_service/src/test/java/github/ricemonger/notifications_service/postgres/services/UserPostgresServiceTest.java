package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.repositories.UserPostgresRepository;
import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.utils.exceptions.client.UserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private UserPostgresRepository userPostgresRepository;

    @Test
    public void getToBeNotifiedUser_should_return_repository_result() {
        String chatId = "123";

        ToBeNotifiedUser user = new ToBeNotifiedUser(chatId, true, false);

        when(userPostgresRepository.findToBeNotifiedUserIdById(1L)).thenReturn(Optional.of(user));

        assertSame(user, userPostgresService.getToBeNotifiedUser(1L));
    }

    @Test
    public void getToBeNotifiedUser_should_throw_if_user_doesnt_exist() {
        when(userPostgresRepository.findToBeNotifiedUserIdById(1L)).thenReturn(Optional.empty());

        assertThrows(UserDoesntExistException.class, () -> userPostgresService.getToBeNotifiedUser(1L));
    }

    @Test
    public void getAllToBeNotifiedUsers_should_return_repository_result() {
        List list = mock(List.class);

        when(userPostgresRepository.findAllToBeNotifiedUsers()).thenReturn(list);

        assertSame(list, userPostgresService.getAllToBeNotifiedUsers());
    }
}