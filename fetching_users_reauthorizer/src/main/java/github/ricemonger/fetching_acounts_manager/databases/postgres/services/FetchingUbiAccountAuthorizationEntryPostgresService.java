package github.ricemonger.fetching_acounts_manager.databases.postgres.services;

import github.ricemonger.fetching_acounts_manager.databases.postgres.repositories.FetchingUbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.fetching_acounts_manager.databases.postgres.services.entity_mappers.FetchingUbiAccountAuthorizationEntryEntityMapper;
import github.ricemonger.fetching_acounts_manager.services.abstractions.AuthorizedUbiUsersDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FetchingUbiAccountAuthorizationEntryPostgresService implements AuthorizedUbiUsersDatabaseService {

    private final FetchingUbiAccountAuthorizationEntryPostgresRepository fetchingUbiAccountAuthorizationEntryPostgresRepository;

    private final FetchingUbiAccountAuthorizationEntryEntityMapper fetchingUbiAccountAuthorizationEntryEntityMapper;

    @Override
    @Transactional
    public void saveAuthorizedUbiUsersAndRemoveOthers(Collection<AuthorizationDTO> authorizedUbiUserList) {
        fetchingUbiAccountAuthorizationEntryPostgresRepository.deleteAll();
        fetchingUbiAccountAuthorizationEntryPostgresRepository.saveAll(fetchingUbiAccountAuthorizationEntryEntityMapper.mapToEntities(authorizedUbiUserList));
    }
}
