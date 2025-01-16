package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.dto_projections.ToBeNotifiedUserProjection;
import github.ricemonger.notifications_service.postgres.repositories.CustomUserPostgresRepository;
import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.utils.exceptions.client.UserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private CustomUserPostgresRepository customUserPostgresRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @Test
    public void getToBeNotifiedUser_should_return_repository_result() {
        ToBeNotifiedUserProjection projection = mock(ToBeNotifiedUserProjection.class);

        ToBeNotifiedUser user = mock(ToBeNotifiedUser.class);

        when(customUserPostgresRepository.findToBeNotifiedUserIdById(1L)).thenReturn(Optional.of(projection));
        when(userEntityMapper.createToBeNotifiedUser(projection)).thenReturn(user);

        assertSame(user, userPostgresService.getToBeNotifiedUser(1L));
    }

    @Test
    public void getToBeNotifiedUser_should_throw_if_user_doesnt_exist() {
        when(customUserPostgresRepository.findToBeNotifiedUserIdById(1L)).thenReturn(Optional.empty());

        assertThrows(UserDoesntExistException.class, () -> userPostgresService.getToBeNotifiedUser(1L));
    }

    @Test
    public void getAllToBeNotifiedUsers_should_return_repository_result() {
        ToBeNotifiedUserProjection projection1 = mock(ToBeNotifiedUserProjection.class);
        ToBeNotifiedUserProjection projection2 = mock(ToBeNotifiedUserProjection.class);

        ToBeNotifiedUser user1 = mock(ToBeNotifiedUser.class);
        ToBeNotifiedUser user2 = mock(ToBeNotifiedUser.class);

        when(customUserPostgresRepository.findAllToBeNotifiedUsers()).thenReturn(List.of(projection1, projection2));
        when(userEntityMapper.createToBeNotifiedUser(projection1)).thenReturn(user1);
        when(userEntityMapper.createToBeNotifiedUser(projection2)).thenReturn(user2);

        List<ToBeNotifiedUser> users = userPostgresService.getAllToBeNotifiedUsers();

        assertTrue(users.stream().anyMatch(user -> user == user1));
        assertTrue(users.stream().anyMatch(user -> user == user2));
        assertEquals(2, users.size());
    }
}