package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private UserPostgresRepository userPostgresRepository;

    @Test
    public void getAllUsers_ForCentralTradeManager_should_return_mapped_repository_result() {
        Collection<ItemEntityDTO> existingItems = List.of();

        UserEntity user1 = mock(UserEntity.class);
        UserEntity user2 = mock(UserEntity.class);
        UserEntity user3 = mock(UserEntity.class);

        when(userPostgresRepository.findAllManageableUsers()).thenReturn(List.of(user1, user2, user3));

        UserForCentralTradeManager userForCentralTradeManager1 = new UserForCentralTradeManager();

        userPostgresService.getAllManageableUsers(existingItems);

        verify(userPostgresRepository, times(1)).findAllManageableUsers();
        verify(user1).toUserForCentralTradeManager(same(existingItems));
        verify(user2).toUserForCentralTradeManager(same(existingItems));
        verify(user3).toUserForCentralTradeManager(same(existingItems));
    }
}