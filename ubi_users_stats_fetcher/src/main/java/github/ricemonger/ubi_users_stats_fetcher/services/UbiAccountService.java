package github.ricemonger.ubi_users_stats_fetcher.services;


import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserUbiAccount;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UbiAccountStatsDatabaseService;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UserUbiAccountEntryDatabaseService;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
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

    public List<UserUbiAccount> findAllUsersUbiAccountEntries() {
        return ubiAccountEntryDatabaseService.findAll();
    }
}
