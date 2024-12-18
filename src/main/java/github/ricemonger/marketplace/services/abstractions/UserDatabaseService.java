package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.UserEntityDTO;
import github.ricemonger.utils.DTOs.UserForCentralTradeManager;

import java.util.List;

public interface UserDatabaseService {
    List<UserEntityDTO> getAllManageableUsers();
}
