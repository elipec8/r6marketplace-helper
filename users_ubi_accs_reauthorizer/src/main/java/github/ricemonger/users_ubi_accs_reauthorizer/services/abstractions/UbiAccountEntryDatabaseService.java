package github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions;

import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;

import java.util.List;

public interface UbiAccountEntryDatabaseService {
    void updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(Long userId, String email, AuthorizationDTO authDTO) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void unlinkUbiAccountStatsForUnauthorizedUsers(List<UserUnauthorizedUbiAccount> unauthorizedUsers);

    List<UserUbiAccountCredentials> findAllUsersUbiAccountCredentials();
}
