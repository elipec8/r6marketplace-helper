package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.UserDatabaseService;
import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserDatabaseService userDatabaseService;

    public List<UserForCentralTradeManager> getAllUserForCentralTradeManagerDTOs(Collection<Item> existingItems) {
        return userDatabaseService.getAllUsersForCentralTradeManager(existingItems);
    }
}
