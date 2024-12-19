package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.ManageableUser;

import java.util.List;

public interface UserDatabaseService {
    List<ManageableUser> getAllManageableUsers();
}
