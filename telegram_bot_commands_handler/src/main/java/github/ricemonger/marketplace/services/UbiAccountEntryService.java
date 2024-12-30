package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryService {

    private final AuthorizationService authorizationService;

    private final TelegramUserUbiAccountEntryDatabaseService telegramUserUbiAccountEntryDatabaseService;

    public void authorizeAndSaveUbiAccountEntry(String chatId, String email, String password, String twoFACode) {

        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGet2FaAuthorizedDTO(email, password, twoFACode);

        saveUbiAccountEntry(chatId, email, authorizationService.encodePassword(password), userAuthorizationDTO);
    }

    public void reauthorizeAndSaveExistingUbiAccountEntryBy2FACode(String chatId, String twoFACode) {
        UbiAccountAuthorizationEntry user = telegramUserUbiAccountEntryDatabaseService.findByChatId(chatId);

        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGet2FaAuthorizedDTOForEncodedPassword(user.getEmail(), user.getEncodedPassword(), twoFACode);

        saveUbiAccountEntry(chatId, user.getEmail(), user.getEncodedPassword(), userAuthorizationDTO);
    }

    public void deleteByChatId(String chatId){
        telegramUserUbiAccountEntryDatabaseService.deleteByChatId(chatId);
    }

    public UbiAccountAuthorizationEntry findByChatId(String chatId){
        return telegramUserUbiAccountEntryDatabaseService.findByChatId(chatId);
    }

    private void saveUbiAccountEntry(String chatId, String email, String encodedPassword, AuthorizationDTO authorizationDTO){
        try {
            telegramUserUbiAccountEntryDatabaseService.save(chatId, buildUbiAccount(email, encodedPassword, authorizationDTO));
        } catch (TelegramUserDoesntExistException e) {
            log.error("Telegram user with chatId {} doesn't exist, but reauthorize ubi user was called fir him with authorizationDto-{}", chatId, authorizationDTO);
        } catch (UbiAccountEntryAlreadyExistsException e) {
            log.error("User with chatId {} already has another Ubi account, but reauthorize ubi user was called for him with authorizationDto-{}", chatId, authorizationDTO);
        }
    }

    private UbiAccountAuthorizationEntry buildUbiAccount(String email, String encodedPassword, AuthorizationDTO authorizationDTO) {
        UbiAccountAuthorizationEntry user = new UbiAccountAuthorizationEntry();
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
