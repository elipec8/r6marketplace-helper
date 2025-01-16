package github.ricemonger.ubi_users_stats_fetcher.postgres.services;

import github.ricemonger.ubi_users_stats_fetcher.postgres.dto_projections.UserAuthorizedUbiAccountProjection;
import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.CustomUserUbiAccountEntryPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserAuthorizedUbiAccount;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserAuthorizedUbiAccountEntryPostgresServiceTest {
    @Autowired
    private UserUbiAccountEntryPostgresService userUbiAccountEntryPostgresService;
    @MockBean
    private CustomUserUbiAccountEntryPostgresRepository customUserUbiAccountEntryPostgresRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void findAll_UserWithUbiAccounts_should_return_mapped_service_result() {
        UserAuthorizedUbiAccountProjection projection1 = Mockito.mock(UserAuthorizedUbiAccountProjection.class);
        UserAuthorizedUbiAccountProjection projection2 = Mockito.mock(UserAuthorizedUbiAccountProjection.class);

        UserAuthorizedUbiAccount dto1 = Mockito.mock(UserAuthorizedUbiAccount.class);
        UserAuthorizedUbiAccount dto2 = Mockito.mock(UserAuthorizedUbiAccount.class);

        Mockito.when(customUserUbiAccountEntryPostgresRepository.findAllUserAuthorizedUbiAccounts()).thenReturn(List.of(projection1, projection2));
        Mockito.when(ubiAccountEntryEntityMapper.createUserUbiAccountEntry(projection1)).thenReturn(dto1);
        Mockito.when(ubiAccountEntryEntityMapper.createUserUbiAccountEntry(projection2)).thenReturn(dto2);

        List<UserAuthorizedUbiAccount> result = userUbiAccountEntryPostgresService.findAllUserAuthorizedUbiAccounts();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(d -> d == dto1));
        assertTrue(result.stream().anyMatch(d -> d == dto2));
    }
}