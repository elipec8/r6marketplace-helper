package github.ricemonger.trades_manager.services.abstractions;

import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;

import java.util.List;

public interface UbiAccountStatsDatabaseService {
    void saveAll(List<UbiAccountStatsEntityDTO> ubiAccounts);
}
