package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.users_ubi_accs_reauthorizer.authorization.AuthorizationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUbiAccountEntryAuthorizationService {

    private final AuthorizationService authorizationService;

    private final UbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    public List<UserUnauthorizedUbiAccount> reauthorizeAllUbiUsersAndGetUnauthorizedList() throws UbiUserAuthorizationServerErrorException {
        List<UserUbiAccountCredentials> users = new ArrayList<>(ubiAccountEntryDatabaseService.findAllUsersUbiAccountCredentials());

        List<UserUnauthorizedUbiAccount> unauthorizedUsers = new ArrayList<>();

        for (UserUbiAccountCredentials user : users) {
            AuthorizationDTO dto = tryToReauthorizeUser(user);

            if (dto.getProfileId() == null) {
                log.info("User with Id {} could not be reauthorized", user.getUserId());
                unauthorizedUsers.add(new UserUnauthorizedUbiAccount(user.getUserId(), user.getEmail()));
                continue;
            }
            ubiAccountEntryDatabaseService.updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(user.getUserId(), user.getEmail(), dto);
        }

        ubiAccountEntryDatabaseService.unlinkUbiAccountStatsForUnauthorizedUsers(unauthorizedUsers);

        return unauthorizedUsers;
    }

    private AuthorizationDTO tryToReauthorizeUser(UserUbiAccountCredentials credentials) {
        AuthorizationDTO dto = tryToReauthorizeByRememberDeviceTicket(credentials);
        if (dto.getProfileId() == null) {
            log.info("User with Id {} could not be reauthorized via rememberDeviceTicket, using ticket instead", credentials.getUserId());
            dto = tryToReauthorizeByTicket(credentials);
        }
        return dto;
    }

    private AuthorizationDTO tryToReauthorizeByRememberDeviceTicket(UserUbiAccountCredentials credentials) {
        try {
            return authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(credentials.getEmail(), credentials.getEncodedPassword(), credentials.getRememberDeviceTicket());
        } catch (UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e) {
            log.info("Ubi error during reauthorization via rememberDeviceTicket for user: {} ", credentials, e);
            return new AuthorizationDTO();
        }
    }

    private AuthorizationDTO tryToReauthorizeByTicket(UserUbiAccountCredentials credentials) {
        try {
            return authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(credentials.getTicket());
        } catch (UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e) {
            log.info("Ubi error during reauthorization via ticket for user: {} ", credentials, e);
            return new AuthorizationDTO();
        }
    }
}
