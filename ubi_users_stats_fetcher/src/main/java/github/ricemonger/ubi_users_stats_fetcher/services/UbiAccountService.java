package github.ricemonger.ubi_users_stats_fetcher.services;


import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserAuthorizedUbiAccount;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UbiAccountStatsDatabaseService;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UserUbiAccountEntryDatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountService {

    private final UserUbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    private final UbiAccountStatsDatabaseService ubiAccountStatsDatabaseService;

    public void saveAllUbiAccountStats(List<UbiAccountStats> ubiAccounts) {
        ubiAccountStatsDatabaseService.saveAll(ubiAccounts);
    }

    public List<UserAuthorizedUbiAccount> findAllUsersUbiAccountEntries() {
        return ubiAccountEntryDatabaseService.findAllUserAuthorizedUbiAccounts();
    }
}
