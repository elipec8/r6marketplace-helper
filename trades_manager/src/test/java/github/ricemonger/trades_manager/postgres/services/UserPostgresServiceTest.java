package github.ricemonger.trades_manager.postgres.services;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.ManageableUserEntity;
import github.ricemonger.trades_manager.postgres.repositories.ManageableUserPostgresRepository;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.user.UserEntityMapper;
import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private ManageableUserPostgresRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @Test
    public void getAllManageableUsers_should_return_mapped_repository_result() {
        ManageableUserEntity entity1 = Mockito.mock(ManageableUserEntity.class);
        ManageableUserEntity entity2 = Mockito.mock(ManageableUserEntity.class);

        Mockito.when(userRepository.findAllManageableUsers()).thenReturn(List.of(entity1, entity2));

        ManageableUser dto1 = Mockito.mock(ManageableUser.class);
        ManageableUser dto2 = Mockito.mock(ManageableUser.class);

        Mockito.when(userEntityMapper.createManageableUser(entity1)).thenReturn(dto1);
        Mockito.when(userEntityMapper.createManageableUser(entity2)).thenReturn(dto2);

        List<ManageableUser> result = userPostgresService.getAllManageableUsers();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto == dto1));
        assertTrue(result.stream().anyMatch(dto -> dto == dto2));
    }
}