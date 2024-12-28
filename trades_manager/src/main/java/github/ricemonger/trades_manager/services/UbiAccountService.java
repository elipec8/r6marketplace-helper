package github.ricemonger.trades_manager.services;


import github.ricemonger.trades_manager.services.DTOs.UserUbiAccountEntry;
import github.ricemonger.trades_manager.services.abstractions.UbiAccountStatsDatabaseService;
import github.ricemonger.trades_manager.services.abstractions.UserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
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

    public void saveAllUbiAccountStats(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        ubiAccountStatsDatabaseService.saveAll(ubiAccounts);
    }

    public List<UserUbiAccountEntry> findAllUsersUbiAccountEntries() {
        return ubiAccountEntryDatabaseService.findAll();
    }
}
