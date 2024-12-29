package github.ricemonger.ubi_users_stats_fetcher.services;

import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UbiAccountStatsDatabaseService;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UserUbiAccountEntryDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class UbiAccountServiceTest {
    @Autowired
    private UbiAccountService ubiAccountService;
    @MockBean
    private UserUbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;
    @MockBean
    private UbiAccountStatsDatabaseService ubiAccountStatsDatabaseService;

    @Test
    public void saveAllUbiAccountStats_should_handle_to_service() {
        List ubiAccountStats = Mockito.mock(List.class);

        ubiAccountService.saveAllUbiAccountStats(ubiAccountStats);

        Mockito.verify(ubiAccountStatsDatabaseService).saveAll(ubiAccountStats);
    }

    @Test
    public void findAllUsersUbiAccountEntries_should_handle_to_service() {
        List userUbiAccount = Mockito.mock(List.class);

        Mockito.when(ubiAccountEntryDatabaseService.findAll()).thenReturn(userUbiAccount);

        assertSame(userUbiAccount, ubiAccountService.findAllUsersUbiAccountEntries());
    }
}