package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UserEntityMapper;
import github.ricemonger.marketplace.services.abstractions.UserDatabaseService;
import github.ricemonger.utils.DTOs.personal.ManageableUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPostgresService implements UserDatabaseService {

    private final UserPostgresRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ManageableUser> getAllManageableUsers() {
        return userRepository.findAllManageableUsers().stream().map(userEntityMapper::createManageableUser).toList();
    }
}
