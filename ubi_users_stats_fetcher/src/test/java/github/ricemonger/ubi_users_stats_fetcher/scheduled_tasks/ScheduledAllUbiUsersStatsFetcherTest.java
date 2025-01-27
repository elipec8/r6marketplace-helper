package github.ricemonger.ubi_users_stats_fetcher.scheduled_tasks;

import github.ricemonger.ubi_users_stats_fetcher.services.UbiUsersStatsFetchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduledAllUbiUsersStatsFetcherTest {
    @Autowired
    ScheduledAllUbiUsersStatsFetcher scheduledAllUbiUsersStatsFetcher;
    @MockBean
    UbiUsersStatsFetchingService ubiUsersStatsFetchingService;

    @Test
    public void fetchAllAuthorizedUbiUsersStats_should_handle_to_service() {
        scheduledAllUbiUsersStatsFetcher.fetchAllAuthorizedUbiUsersStats();

        verify(ubiUsersStatsFetchingService).fetchAllAuthorizedUbiUsersStats();
    }
}