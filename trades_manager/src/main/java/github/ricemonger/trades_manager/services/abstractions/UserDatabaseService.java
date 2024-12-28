package github.ricemonger.trades_manager.services.abstractions;

import github.ricemonger.trades_manager.services.DTOs.ManageableUser;

import java.util.List;

public interface UserDatabaseService {
    List<ManageableUser> getAllManageableUsers();
}
