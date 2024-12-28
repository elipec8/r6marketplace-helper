package github.ricemonger.trades_manager.services.abstractions;

import github.ricemonger.trades_manager.services.DTOs.UserUbiAccountEntry;

import java.util.List;

public interface UserUbiAccountEntryDatabaseService {
    List<UserUbiAccountEntry> findAll();
}
