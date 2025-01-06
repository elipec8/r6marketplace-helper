package github.ricemonger.ubi_users_stats_fetcher.postgres.services;


import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user.UbiAccountStatsEntityMapper;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UbiAccountStats;
import github.ricemonger.ubi_users_stats_fetcher.services.abstractions.UbiAccountStatsDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbiAccountStatsPostgresService implements UbiAccountStatsDatabaseService {

    private final UbiAccountStatsPostgresRepository ubiAccountStatsRepository;

    private final UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    @Override
    @Transactional
    public void saveAll(List<UbiAccountStats> ubiAccounts) {
        ubiAccountStatsRepository.saveAll(ubiAccountStatsEntityMapper.createEntities(ubiAccounts));
    }
}
