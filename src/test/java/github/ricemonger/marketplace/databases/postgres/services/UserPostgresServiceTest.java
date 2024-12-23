package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UserEntityMapper;
import github.ricemonger.utils.DTOs.personal.ManageableUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private UserPostgresRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @Test
    public void getAllManageableUsers_should_return_all_mapped_entities() {
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        List<UserEntity> userEntities = List.of(userEntity1, userEntity2);
        when(userRepository.findAllManageableUsers()).thenReturn(userEntities);
        ManageableUser manageableUser1 = new ManageableUser();
        ManageableUser manageableUser2 = new ManageableUser();
        when(userEntityMapper.createManageableUser(same(userEntity1))).thenReturn(manageableUser1);
        when(userEntityMapper.createManageableUser(same(userEntity2))).thenReturn(manageableUser2);

        List<ManageableUser> result = userPostgresService.getAllManageableUsers();

        List<ManageableUser> expected = List.of(manageableUser1, manageableUser2);

        assertEquals(expected.size(), result.size());
        assertTrue(result.stream().allMatch(res -> expected.stream().anyMatch(exp -> exp == res)));
    }
}