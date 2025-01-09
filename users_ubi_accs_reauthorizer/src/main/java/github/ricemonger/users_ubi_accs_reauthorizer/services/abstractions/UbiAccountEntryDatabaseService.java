package github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions;

import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UnauthorizedAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;

import java.util.List;

public interface UbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(Long userId, String email, AuthorizationDTO authDTO) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void deleteUbiAccountStatsForUnauthorizedUsers(List<UnauthorizedAccount> unauthorizedUsers);

    List<UserUbiCredentials> findAllUsersUbiCredentials();
}
