package github.ricemonger.fetching_acounts_manager.services.abstractions;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;

import java.util.Collection;

public interface AuthorizedUbiUsersDatabaseService {
    void saveAuthorizedUbiUsersAndRemoveOthers(Collection<AuthorizationDTO> authorizedUbiUserList);
}
