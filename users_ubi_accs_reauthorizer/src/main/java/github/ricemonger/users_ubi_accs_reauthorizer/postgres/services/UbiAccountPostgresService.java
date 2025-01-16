package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services;


import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.CustomUbiAccountEntryPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories.UbiAccountStatsPostgresRepository;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
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

    private final CustomUbiAccountEntryPostgresRepository customUbiAccountEntryPostgresRepository;

    private final UbiAccountStatsPostgresRepository ubiAccountStatsPostgresRepository;

    private final UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Override
    @Transactional
    public void updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(Long userId, String email, AuthorizationDTO authDTO) {
        customUbiAccountEntryPostgresRepository.updateUserUbiCredentials(ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, authDTO));

        String authorizedProfileId = authDTO.getProfileId();
        String existingProfileId = customUbiAccountEntryPostgresRepository.findUbiAccountStatsProfileIdByUserIdAndEmail(userId, email).orElse(null);

        if (existingProfileId == null || !Objects.equals(existingProfileId, authorizedProfileId)) {
            if (!ubiAccountStatsPostgresRepository.existsById(authorizedProfileId)) {
                ubiAccountStatsPostgresRepository.save(new UbiAccountStatsEntity(authorizedProfileId));
            }
            customUbiAccountEntryPostgresRepository.linkUbiAccountStatsByUserIdAndEmail(userId, email, authorizedProfileId);
        }
    }

    @Override
    @Transactional
    public void unlinkUbiAccountStatsForUnauthorizedUsers(List<UserUnauthorizedUbiAccount> unauthorizedUsers) {
        customUbiAccountEntryPostgresRepository.unlinkAllUbiAccountStatsForUnauthorizedUsers(unauthorizedUsers.stream().map(ubiAccountEntryEntityMapper::createUnauthorizedAccountProjection).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserUbiAccountCredentials> findAllUsersUbiAccountCredentials() {
        return customUbiAccountEntryPostgresRepository.findAllUsersUbiCredentials().stream().map(ubiAccountEntryEntityMapper::createUserUbiAccountCredentials).toList();
    }
}
