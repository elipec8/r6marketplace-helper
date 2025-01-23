package github.ricemonger.fetching_acounts_manager.services;

import github.ricemonger.fetching_acounts_manager.services.abstractions.AuthorizedUbiUsersDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthorizedUbiUsersService {

    private final AuthorizedUbiUsersDatabaseService authorizedUbiUsersDatabaseService;

    public void saveAuthorizedUbiUsersAndRemoveOthers(Collection<AuthorizationDTO> authorizedUbiUserList) {
        authorizedUbiUsersDatabaseService.saveAuthorizedUbiUsersAndRemoveOthers(authorizedUbiUserList);
    }
}
