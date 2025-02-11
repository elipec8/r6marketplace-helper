package github.ricemonger.trades_manager.postgres.services;

import github.ricemonger.trades_manager.postgres.repositories.CustomUserPostgresRepository;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.user.UserEntityMapper;
import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
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
    private CustomUserPostgresRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @Test
    public void getAllManageableUsers_should_return_mapped_repository_result() {
        UserEntity projection1 = Mockito.mock(UserEntity.class);
        UserEntity projection2 = Mockito.mock(UserEntity.class);

        Mockito.when(userRepository.findAllManageableUsers()).thenReturn(List.of(projection1, projection2));

        ManageableUser dto1 = Mockito.mock(ManageableUser.class);
        ManageableUser dto2 = Mockito.mock(ManageableUser.class);

        Mockito.when(userEntityMapper.createManageableUser(projection1)).thenReturn(dto1);
        Mockito.when(userEntityMapper.createManageableUser(projection2)).thenReturn(dto2);

        List<ManageableUser> result = userPostgresService.getAllManageableUsers();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto == dto1));
        assertTrue(result.stream().anyMatch(dto -> dto == dto2));
    }
}