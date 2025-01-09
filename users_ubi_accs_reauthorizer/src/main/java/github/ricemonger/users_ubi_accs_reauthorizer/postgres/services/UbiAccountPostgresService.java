package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services;


import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntityId;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryCredentialsPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UnauthorizedAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UbiAccountPostgresService implements UbiAccountEntryDatabaseService {

    private final UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;

    private final UbiAccountEntryCredentialsPostgresRepository ubiAccountEntryCredentialsRepository;

    private final UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Override
    @Transactional
    public void saveAuthorizationInfo(Long userId, String email, AuthorizationDTO authDTO) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException {
        UbiAccountEntryEntity ubiAccountEntryEntity = ubiAccountEntryPostgresRepository.findById(new UbiAccountEntryEntityId(userId, email)).orElse(null);

        if (ubiAccountEntryEntity == null) {
            log.error("User with Id {} doesn't have linked ubi account with email-{}", userId, email);
            throw new TelegramUserDoesntExistException("User with Id " + userId + " doesn't have linked ubi account with email-" + email);
        } else if (authDTO.getProfileId().equals(ubiAccountEntryEntity.getUbiProfileId_())) {
            ubiAccountEntryEntity.setAuthorizationDTOFields(authDTO);
            ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntity);
        } else {
            throw new UbiAccountEntryAlreadyExistsException("User with Id " + userId + " already has another Ubi account");
        }
    }

    @Override
    @Transactional
    public void deleteUbiAccountStatsForUnauthorizedUsers(List<UnauthorizedAccount> unauthorizedUsers) {
        ubiAccountEntryPostgresRepository.deleteAllUbiAccountStatsForUnauthorizedUsers(unauthorizedUsers.stream().map(ubiAccountEntryEntityMapper::createUnauthorizedAccountProjection).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserUbiCredentials> findAllUsersUbiCredentials() {
        return ubiAccountEntryCredentialsRepository.findAll().stream().map(ubiAccountEntryEntityMapper::createUserUbiCredentials).toList();
    }
}
