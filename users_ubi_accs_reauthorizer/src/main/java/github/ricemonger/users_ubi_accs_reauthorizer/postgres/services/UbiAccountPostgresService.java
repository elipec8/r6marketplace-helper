package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services;


import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UbiAccountPostgresService implements UbiAccountEntryDatabaseService {

    private final UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;

    private final UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;

    private final UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Override
    @Transactional
    public void updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(Long userId, String email, AuthorizationDTO authDTO){
        ubiAccountEntryPostgresRepository.updateUserUbiCredentials(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, authDTO));

        String authorizedProfileId = authDTO.getProfileId();
        String existingProfileId = ubiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email).orElse(null);

        if(existingProfileId == null || !Objects.equals(existingProfileId, authorizedProfileId)) {
            if(!ubiAccountStatsPostgresRepository.existsById(authorizedProfileId)) {
                ubiAccountStatsPostgresRepository.save(new UbiAccountStatsEntity(authorizedProfileId));
            }
            ubiAccountEntryPostgresRepository.linkUbiAccountStatsByUserIdAndEmail(userId, email, authorizedProfileId);
        }
    }

    @Override
    @Transactional
    public void unlinkUbiAccountStatsForUnauthorizedUsers(List<UserUnauthorizedUbiAccount> unauthorizedUsers) {
        ubiAccountEntryPostgresRepository.unlinkAllUbiAccountStatsForUnauthorizedUsers(unauthorizedUsers.stream().map(ubiAccountEntryEntityMapper::createUnauthorizedAccountProjection).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserUbiAccountCredentials> findAllUsersUbiAccountCredentials() {
        return ubiAccountEntryPostgresRepository.findAllUsersUbiCredentials().stream().map(ubiAccountEntryEntityMapper::createUserUbiAccountCredentials).toList();
    }
}
