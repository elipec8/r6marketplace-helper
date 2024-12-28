package github.ricemonger.trades_manager.services;

import github.ricemonger.trades_manager.services.abstractions.UserDatabaseService;
import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserDatabaseService userDatabaseService;

    public List<ManageableUser> getAllManageableUsers() {
        return userDatabaseService.getAllManageableUsers();
    }
}
