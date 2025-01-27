package github.ricemonger.ubi_users_stats_fetcher.scheduled_tasks;

import github.ricemonger.ubi_users_stats_fetcher.services.UbiUsersStatsFetchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllUbiUsersStatsFetcher {

    private final UbiUsersStatsFetchingService ubiUsersStatsFetchingService;

    @Scheduled(fixedRateString = "${app.scheduling.fixedRate}", initialDelayString = "${app.scheduling.initialDelay}")
    public void fetchAllAuthorizedUbiUsersStats() {
        ubiUsersStatsFetchingService.fetchAllAuthorizedUbiUsersStats();
    }
}
