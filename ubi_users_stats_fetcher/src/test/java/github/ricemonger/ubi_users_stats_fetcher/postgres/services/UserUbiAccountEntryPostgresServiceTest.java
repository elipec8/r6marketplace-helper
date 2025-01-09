package github.ricemonger.ubi_users_stats_fetcher.postgres.services;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.UserUbiAccountEntryPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserUbiAccount;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserUbiAccountEntryPostgresServiceTest {
    @Autowired
    private UserUbiAccountEntryPostgresService userUbiAccountEntryPostgresService;
    @MockBean
    private UserUbiAccountEntryPostgresRepository userUbiAccountEntryPostgresRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void findAll_UserWithUbiAccounts_should_return_mapped_service_result() {
        UserUbiAccountEntryEntity entity1 = Mockito.mock(UserUbiAccountEntryEntity.class);
        UserUbiAccountEntryEntity entity2 = Mockito.mock(UserUbiAccountEntryEntity.class);

        UserUbiAccount dto1 = Mockito.mock(UserUbiAccount.class);
        UserUbiAccount dto2 = Mockito.mock(UserUbiAccount.class);

        Mockito.when(userUbiAccountEntryPostgresRepository.findAllUserWithAuthorizedUbiAccounts()).thenReturn(List.of(entity1, entity2));
        Mockito.when(ubiAccountEntryEntityMapper.createUserUbiAccountEntry(entity1)).thenReturn(dto1);
        Mockito.when(ubiAccountEntryEntityMapper.createUserUbiAccountEntry(entity2)).thenReturn(dto2);

        List<UserUbiAccount> result = userUbiAccountEntryPostgresService.findAllUserWithUbiAccounts();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(d -> d == dto1));
        assertTrue(result.stream().anyMatch(d -> d == dto2));
    }
}