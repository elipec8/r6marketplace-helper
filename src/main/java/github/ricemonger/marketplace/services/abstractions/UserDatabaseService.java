package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.Item;

import java.util.Collection;
import java.util.List;

public interface UserDatabaseService {
    List<UserForCentralTradeManager> getAllUsersForCentralTradeManager();
}
