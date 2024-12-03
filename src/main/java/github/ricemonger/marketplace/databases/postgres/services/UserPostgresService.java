package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
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
public class UserPostgresService implements UserDatabaseService {

    private final UserPostgresRepository userRepository;

    @Override
    public List<UserForCentralTradeManager> getAllUsersForCentralTradeManager(Collection<Item> existingItems) {
        return userRepository.findAllManageableUsers().stream().map(userEntity -> userEntity.toUserForCentralTradeManagerDTO(existingItems)).toList();
    }
}
