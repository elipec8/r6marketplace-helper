package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramUserUbiAccountEntryServiceTest {
    @Autowired
    private TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private TelegramUserUbiAccountEntryDatabaseService telegramUserUbiAccountEntryDatabaseService;

    @Test
    public void authorizeAndSaveUser_should_handle_to_services() {
        String email = "email";
        String password = "password";
        String twoFaCode = "twoFaCode";
        String encodedPassword = "encodedPassword";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setTicket("ticket");
        when(authorizationService.authorizeAndGet2FaAuthorizedDTO(email, password, twoFaCode)).thenReturn(dto);
        when(authorizationService.encodePassword(password)).thenReturn(encodedPassword);

        telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", email, password, twoFaCode);

        verify(authorizationService).authorizeAndGet2FaAuthorizedDTO(email, password, twoFaCode);

        verify(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo("chatId", buildUbiAccount(email, encodedPassword, dto));
    }

    @Test
    public void reauthorizeAndSaveExistingUserBy2FACode_should_reauthorize_using_email_and_encodedPassword_from_db_and_twoFaCode() {
        String email = "email";
        String password = "password";
        String twoFaCode = "twoFaCode";
        String encodedPassword = "encodedPassword";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setTicket("ticket");
        UbiAccountAuthorizationEntry authEntry = new UbiAccountAuthorizationEntry();
        authEntry.setEmail(email);
        authEntry.setEncodedPassword(encodedPassword);
        when(telegramUserUbiAccountEntryDatabaseService.findAuthorizationInfoByChatId(any())).thenReturn(authEntry);
        when(authorizationService.authorizeAndGet2FaAuthorizedDTOForEncodedPassword(email, encodedPassword, twoFaCode)).thenReturn(dto);

        telegramUserUbiAccountEntryService.reauthorizeAndSaveExistingUserBy2FACode("chatId", twoFaCode);

        verify(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo("chatId", buildUbiAccount(email, encodedPassword, dto));
    }

    @Test
    public void deleteByChatId_should_handle_to_service() {
        telegramUserUbiAccountEntryService.deleteByChatId("chatId");

        verify(telegramUserUbiAccountEntryDatabaseService).deleteAuthorizationInfoByChatId("chatId");
    }

    @Test
    public void deleteByChatId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).deleteAuthorizationInfoByChatId(any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteByChatId("chatId"));
    }

    @Test
    public void findByChatId_should_return_ubi_account_entry() {
        String chatId = "chatId";
        UbiAccountAuthorizationEntry entry = new UbiAccountAuthorizationEntry();
        entry.setEmail("email");
        when(telegramUserUbiAccountEntryDatabaseService.findAuthorizationInfoByChatId(chatId)).thenReturn(entry);

        assertEquals(entry, telegramUserUbiAccountEntryService.findByChatId(chatId));

        verify(telegramUserUbiAccountEntryDatabaseService).findAuthorizationInfoByChatId(chatId);
    }

    @Test
    public void findByChatId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).findAuthorizationInfoByChatId(any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId("chatId"));
    }

    @Test
    public void findByChatId_should_throw_if_ubi_account_entry_doesnt_exist() {
        doThrow(UbiAccountEntryDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).findAuthorizationInfoByChatId(any());

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId("chatId"));
    }

    private UbiAccountAuthorizationEntry buildUbiAccount(String email, String password, AuthorizationDTO authorizationDTO) {
        UbiAccountAuthorizationEntry user = new UbiAccountAuthorizationEntry();
        user.setEmail(email);
        user.setEncodedPassword(password);

        user.setUbiProfileId(authorizationDTO.getProfileId());
        user.setUbiSessionId(authorizationDTO.getSessionId());
        user.setUbiAuthTicket(authorizationDTO.getTicket());
        user.setUbiSpaceId(authorizationDTO.getSpaceId());
        user.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
        user.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
        return user;
    }
}