package github.ricemonger.trades_manager.postgres.services;

import github.ricemonger.trades_manager.postgres.repositories.UserUbiAccountEntryPostgresRepository;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.trades_manager.services.DTOs.UserUbiAccountEntry;
import github.ricemonger.trades_manager.services.abstractions.UserUbiAccountEntryDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUbiAccountEntryPostgresService implements UserUbiAccountEntryDatabaseService {

    private final UserUbiAccountEntryPostgresRepository userUbiAccountEntryPostgresRepository;

    private final UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserUbiAccountEntry> findAll() {
        return userUbiAccountEntryPostgresRepository.findAll().stream().map(ubiAccountEntryEntityMapper::createUserUbiAccountEntry).toList();
    }
}
