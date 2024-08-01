package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUbiAccountService {

    private final TelegramUserUbiAccountEntryDatabaseService telegramUserUbiAccountEntryDatabaseService;

    private final AuthorizationService authorizationService;

    public UbiAccount findByChatId(String chatId) {
        return telegramUserUbiAccountEntryDatabaseService.findByChatId(chatId);
    }

    public void deleteByChatId(String chatId) {
        telegramUserUbiAccountEntryDatabaseService.deleteByChatId(chatId);
    }

    public void authorizeAndSaveUser(String chatId, String email, String password) throws
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGetDTO(email, password);

        telegramUserUbiAccountEntryDatabaseService.save(chatId, buildUbiAccount(email, authorizationService.getEncodedPassword(password), userAuthorizationDTO));
    }

    public List<UbiAccountWithTelegram> reauthorizeAllUbiUsersAndGetUnauthorizedList() {
        List<UbiAccountWithTelegram> users = new ArrayList<>(telegramUserUbiAccountEntryDatabaseService.findAll());

        List<UbiAccountWithTelegram> unauthorizedUsers = new ArrayList<>();

        for (UbiAccountWithTelegram user : users) {
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

        telegramUserUbiAccountEntryDatabaseService.save(chatId, buildUbiAccount(email, encodedPassword, authorizationDTO));
    }

    private UbiAccount buildUbiAccount(String email, String password, AuthorizationDTO authorizationDTO) {
        UbiAccount user = new UbiAccount();
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
