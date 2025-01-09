package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.users_ubi_accs_reauthorizer.authorization.AuthorizationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UnauthorizedAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUbiAccountEntryService {

    private final AuthorizationService authorizationService;

    private final UbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    public List<UnauthorizedAccount> reauthorizeAllUbiUsersAndGetUnauthorizedList() {
        List<UserUbiCredentials> users = new ArrayList<>(ubiAccountEntryDatabaseService.findAllUsersUbiCredentials());

        List<UnauthorizedAccount> unauthorizedUsers = new ArrayList<>();

        for (UserUbiCredentials user : users) {
            try {
                AuthorizationDTO dto = authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(user.getEmail(), user.getEncodedPassword(), user.getRememberDeviceTicket());
                if (dto.getProfileId() == null) {
                    log.error("User with Id {} could not be reauthorized, because of invalid rememberDeviceTicket", user.getUserId());
                    unauthorizedUsers.add(new UnauthorizedAccount(user.getUserId(), user.getEmail()));
                    continue;
                }
                saveAuthorizationInfo(user.getUserId(), user.getEmail(), dto);
            } catch (UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e) {
                unauthorizedUsers.add(new UnauthorizedAccount(user.getUserId(), user.getEmail()));
            }
        }

        ubiAccountEntryDatabaseService.deleteUbiAccountStatsForUnauthorizedUsers(unauthorizedUsers);

        return unauthorizedUsers;
    }

    private void saveAuthorizationInfo(Long userId, String email, AuthorizationDTO authorizationDTO) throws TelegramUserDoesntExistException,
            UbiAccountEntryAlreadyExistsException {
        try {
            ubiAccountEntryDatabaseService.saveAuthorizationInfo(userId, email, authorizationDTO);
        } catch (TelegramUserDoesntExistException e) {
            log.error("Telegram user with Id {} doesn't exist, but reauthorize ubi user was called fir him with authorizationDto-{}", userId, authorizationDTO);
        } catch (UbiAccountEntryAlreadyExistsException e) {
            log.error("User with Id {} already has another Ubi account, but reauthorize ubi user was called for him with authorizationDto-{}", userId, authorizationDTO);
        }
    }
}
