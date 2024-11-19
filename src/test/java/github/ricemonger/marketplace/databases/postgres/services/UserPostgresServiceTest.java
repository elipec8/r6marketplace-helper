package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @MockBean
    private UserPostgresRepository userPostgresRepository;

    @Test
    public void getAllTradingUsers_should_return_mapped_repository_result() {
        UserEntity user1 = new UserEntity();

        UserEntity user2 = new UserEntity();

        UserEntity user3 = new UserEntity();


        when(userPostgresRepository.findAllManageableUsers()).thenReturn(List.of(user1, user2, user3));

    }
}