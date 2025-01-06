package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.repositories.UserPostgresRepository;
import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import github.ricemonger.utils.exceptions.client.UserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostgresService implements UserDatabaseService {

    private final UserPostgresRepository userPostgresRepository;

    @Override
    public ToBeNotifiedUser getToBeNotifiedUser(Long userId) {
        return userPostgresRepository.findToBeNotifiedUserIdById(userId).orElseThrow(() -> new UserDoesntExistException("User with id " + userId + " doesn't exist"));
    }

    @Override
    public List<ToBeNotifiedUser> getAllToBeNotifiedUsers() {
        return userPostgresRepository.findAllToBeNotifiedUsers();
    }
}
