package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.repositories.UserPostgresRepository;
import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostgresService implements UserDatabaseService {

    private final UserPostgresRepository userPostgresRepository;

    @Override
    public String getUserChatId(Long usedId) {
        return userPostgresRepository.findTelegramUserChatIdById(usedId).orElseThrow(() -> new TelegramUserDoesntExistException("User with id: " + usedId + " doesn't exist"));
    }
}
