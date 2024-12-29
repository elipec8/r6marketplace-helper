package github.ricemonger.ubi_users_stats_fetcher.services.abstractions;

import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;

import java.util.List;

public interface UbiAccountStatsDatabaseService {
    void saveAll(List<UbiAccountStats> ubiAccounts);
}
