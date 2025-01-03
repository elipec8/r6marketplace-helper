package github.ricemonger.notifications_service.services;

import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDatabaseService userDatabaseService;

    public String getUserChatId(Long userId) {
        return userDatabaseService.getUserChatId(userId);
    }
}
