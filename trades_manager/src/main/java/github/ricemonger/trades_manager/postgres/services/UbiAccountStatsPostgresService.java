package github.ricemonger.trades_manager.postgres.services;

import github.ricemonger.trades_manager.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.user.UbiAccountStatsEntityMapper;
import github.ricemonger.trades_manager.services.abstractions.UbiAccountStatsDatabaseService;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
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
    public void saveAll(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        ubiAccountStatsRepository.saveAll(ubiAccountStatsEntityMapper.createEntities(ubiAccounts));
    }
}
