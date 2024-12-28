package github.ricemonger.trades_manager.services.abstractions;

import github.ricemonger.utils.DTOs.personal.ManageableUser;

import java.util.List;

public interface UserDatabaseService {
    List<ManageableUser> getAllManageableUsers();
}
