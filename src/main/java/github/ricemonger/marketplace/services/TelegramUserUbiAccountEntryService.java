package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryEntityDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountEntryEntityDTOWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import github.ricemonger.utils.DTOs.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
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
public class TelegramUserUbiAccountEntryService {

    private final AuthorizationService authorizationService;

    private final TelegramUserUbiAccountEntryDatabaseService telegramUserUbiAccountEntryDatabaseService;

    public void authorizeAndSaveUser(String chatId, String email, String password, String twoFACode) throws
            TelegramUserDoesntExistException,
            UbiAccountEntryAlreadyExistsException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGet2FaAuthorizedDTO(email, password, twoFACode);

        saveAuthorizationInfo(chatId, email, authorizationService.encodePassword(password), userAuthorizationDTO);
    }

    public void reauthorizeAndSaveExistingUserBy2FACode(String chatId, String twoFACode) {
        UbiAccountAuthorizationEntryEntityDTO user = telegramUserUbiAccountEntryDatabaseService.findAuthorizationInfoByChatId(chatId);

        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGet2FaAuthorizedDTOForEncodedPassword(user.getEmail(), user.getEncodedPassword(), twoFACode);

        saveAuthorizationInfo(chatId, user.getEmail(), user.getEncodedPassword(), userAuthorizationDTO);
    }

    public void saveAllUbiAccountStats(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        telegramUserUbiAccountEntryDatabaseService.saveAllUbiAccountStats(ubiAccounts);
    }

    public void deleteByChatId(String chatId) throws TelegramUserDoesntExistException {
        telegramUserUbiAccountEntryDatabaseService.deleteAuthorizationInfoByChatId(chatId);
    }

    public List<UbiAccountAuthorizationEntryWithTelegram> reauthorizeAllUbiUsersAndGetUnauthorizedList() {
        List<UbiAccountAuthorizationEntryWithTelegram> users = new ArrayList<>(telegramUserUbiAccountEntryDatabaseService.findAllAuthorizationInfoForTelegram());

        List<UbiAccountAuthorizationEntryWithTelegram> unauthorizedUsers = new ArrayList<>();

        for (UbiAccountAuthorizationEntryWithTelegram user : users) {
            try {
                AuthorizationDTO dto = authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithRememberDeviceTicket(user.getEmail(), user.getEncodedPassword(), user.getUbiRememberDeviceTicket());
                saveAuthorizationInfo(user.getChatId(), user.getEmail(), user.getEncodedPassword(), dto);
            } catch (UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e) {
                unauthorizedUsers.add(user);
            }
        }

        return unauthorizedUsers;
    }

    public UbiAccountAuthorizationEntryEntityDTO findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException {
        return telegramUserUbiAccountEntryDatabaseService.findAuthorizationInfoByChatId(chatId);
    }

    public List<UbiAccountEntryEntityDTOWithTelegram> findAllFUbiAccountEntriesWithTelegram() {
        return telegramUserUbiAccountEntryDatabaseService.findAllForTelegram();
    }

    private void saveAuthorizationInfo(String chatId, String email, String encodedPassword, AuthorizationDTO authorizationDTO) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException {
        try {
            telegramUserUbiAccountEntryDatabaseService.saveAuthorizationInfo(chatId, buildUbiAccount(email, encodedPassword, authorizationDTO));
        } catch (TelegramUserDoesntExistException e) {
            log.error("Telegram user with chatId {} doesn't exist, but reauthorize ubi user was called fir him with authorizationDto-{}", chatId, authorizationDTO);
        } catch (UbiAccountEntryAlreadyExistsException e) {
            log.error("User with chatId {} already has another Ubi account, but reauthorize ubi user was called for him with authorizationDto-{}", chatId, authorizationDTO);
        }
    }

    private UbiAccountAuthorizationEntryEntityDTO buildUbiAccount(String email, String encodedPassword, AuthorizationDTO authorizationDTO) {
        UbiAccountAuthorizationEntryEntityDTO user = new UbiAccountAuthorizationEntryEntityDTO();
        user.setEmail(email);
        user.setEncodedPassword(encodedPassword);

        user.setUbiProfileId(authorizationDTO.getProfileId());
        user.setUbiSessionId(authorizationDTO.getSessionId());
        user.setUbiAuthTicket(authorizationDTO.getTicket());
        user.setUbiSpaceId(authorizationDTO.getSpaceId());
        user.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
        user.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
        return user;
    }
}
