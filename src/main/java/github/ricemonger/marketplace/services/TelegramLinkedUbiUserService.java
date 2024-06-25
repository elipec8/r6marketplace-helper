package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.UbiAccountDatabaseService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramLinkedUbiUserService {

    private final UbiAccountDatabaseService ubiAccountDatabaseService;

    private final AuthorizationService authorizationService;

    public Collection<UbiAccount> findAllByLinkedTelegramUserChatId(String chatId) {
        return ubiAccountDatabaseService.findAllByChatId(chatId);
    }

    public void deleteAllByLinkedTelegramUserChatId(String chatId) {
        ubiAccountDatabaseService.deleteAllByChatId(chatId);
    }

    public void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String email) {
        ubiAccountDatabaseService.deleteById(chatId, email);
    }

    public void authorizeAndSaveUser(String chatId, String email, String password) throws
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGetDTO(email, password);

        ubiAccountDatabaseService.save(buildUbiUser(chatId, email, authorizationService.getEncodedPassword(password), userAuthorizationDTO));
    }

    public List<UbiAccountWithTelegram> reauthorizeAllUbiUsersAndGetUnauthorizedList() {
        List<UbiAccount> users = new ArrayList<>(ubiAccountDatabaseService.findAll());

        List<UbiAccount> unauthorizedUsers = new ArrayList<>();

        for (UbiAccount user : users) {
            try {
                reauthorizeAndSaveUser(user.getChatId(), user.getEmail(), user.getEncodedPassword());
            } catch (UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e) {
                unauthorizedUsers.add(user);
            }
        }

        return unauthorizedUsers;
    }

    private void reauthorizeAndSaveUser(String chatId, String email, String encodedPassword) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        AuthorizationDTO authorizationDTO = authorizationService.authorizeAndGetDtoForEncodedPassword(email, encodedPassword);

        ubiAccountDatabaseService.save(buildUbiUser(chatId, email, encodedPassword, authorizationDTO));
    }

    private UbiAccount buildUbiUser(String chatId, String email, String password, AuthorizationDTO authorizationDTO) {
        UbiAccount user = new UbiAccount();

        user.setChatId(chatId);
        user.setEmail(email);
        user.setEncodedPassword(password);

        user.setUbiProfileId(authorizationDTO.getProfileId());
        user.setUbiSessionId(authorizationDTO.getSessionId());
        user.setUbiAuthTicket(authorizationDTO.getTicket());
        user.setUbiSpaceId(authorizationDTO.getSpaceId());
        user.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
        user.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
        user.setUbiTwoFactorAuthTicket(authorizationDTO.getTwoFactorAuthenticationTicket());
        return user;
    }
}
