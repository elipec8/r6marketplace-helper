package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        String encodedPassword = "encodedPassword";
        AuthorizationDTO dto = new AuthorizationDTO();
        dto.setTicket("ticket");
        when(authorizationService.authorizeAndGetDTO(email, password)).thenReturn(dto);
        when(authorizationService.getEncodedPassword(password)).thenReturn(encodedPassword);

        telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", email, password);

        verify(authorizationService).authorizeAndGetDTO(email, password);

        verify(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo("chatId", buildUbiAccount(email, encodedPassword, dto));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo(any(), any());
        when(authorizationService.authorizeAndGetDTO(any(), any())).thenReturn(new AuthorizationDTO());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", "email", "password"));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_user_already_have_another_ubi_account_entry() {
        doThrow(UbiAccountEntryAlreadyExistsException.class).when(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo(any(), any());
        when(authorizationService.authorizeAndGetDTO(any(), any())).thenReturn(new AuthorizationDTO());

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", "email", "password"));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_client_authorization_exception_was_thrown() {
        doThrow(UbiUserAuthorizationClientErrorException.class).when(authorizationService).authorizeAndGetDTO(any(), any());

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", "email", "password"));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_server_authorization_exception_was_thrown() {
        doThrow(UbiUserAuthorizationServerErrorException.class).when(authorizationService).authorizeAndGetDTO(any(), any());

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", "email", "password"));
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
    public void reauthorizeAllUbiUsersAndGetUnauthorizedList_should_reauthorize_all_users_and_return_unauthorized() {
        UbiAccountAuthorizationEntry authorizedEntry = new UbiAccountAuthorizationEntry();
        authorizedEntry.setEmail("email");
        authorizedEntry.setEncodedPassword("encodedPassword");

        UbiAccountAuthorizationEntry clientErrorEntry = new UbiAccountAuthorizationEntry();
        authorizedEntry.setEmail("email1");
        authorizedEntry.setEncodedPassword("encodedPassword1");

        UbiAccountAuthorizationEntry serverErrorEntry = new UbiAccountAuthorizationEntry();
        authorizedEntry.setEmail("email2");
        authorizedEntry.setEncodedPassword("encodedPassword2");

        List<UbiAccountAuthorizationEntryWithTelegram> authUsers = new ArrayList<>();

        authUsers.add(new UbiAccountAuthorizationEntryWithTelegram("1", authorizedEntry));
        authUsers.add(new UbiAccountAuthorizationEntryWithTelegram("2", authorizedEntry));
        authUsers.add(new UbiAccountAuthorizationEntryWithTelegram("3", authorizedEntry));

        List<UbiAccountAuthorizationEntryWithTelegram> authUsersWithDatabaseExceptions = new ArrayList<>();

        authUsersWithDatabaseExceptions.add(new UbiAccountAuthorizationEntryWithTelegram("6", authorizedEntry));
        authUsersWithDatabaseExceptions.add(new UbiAccountAuthorizationEntryWithTelegram("7", authorizedEntry));

        when(authorizationService.authorizeAndGetDtoForEncodedPassword(authorizedEntry.getEmail(), authorizedEntry.getEncodedPassword())).thenReturn(new AuthorizationDTO());
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo(eq("6"), eq(authorizedEntry));
        doThrow(UbiAccountEntryAlreadyExistsException.class).when(telegramUserUbiAccountEntryDatabaseService).saveAuthorizationInfo(eq("7"), eq(authorizedEntry));

        List<UbiAccountAuthorizationEntryWithTelegram> unAuthUsers = new ArrayList<>();
        unAuthUsers.add(new UbiAccountAuthorizationEntryWithTelegram("4", clientErrorEntry));
        unAuthUsers.add(new UbiAccountAuthorizationEntryWithTelegram("5", serverErrorEntry));

        doThrow(UbiUserAuthorizationClientErrorException.class).when(authorizationService).authorizeAndGetDtoForEncodedPassword(clientErrorEntry.getEmail(), clientErrorEntry.getEncodedPassword());
        doThrow(UbiUserAuthorizationServerErrorException.class).when(authorizationService).authorizeAndGetDtoForEncodedPassword(serverErrorEntry.getEmail(), serverErrorEntry.getEncodedPassword());

        List<UbiAccountAuthorizationEntryWithTelegram> allUsers = new ArrayList<>();
        allUsers.addAll(authUsers);
        allUsers.addAll(authUsersWithDatabaseExceptions);
        allUsers.addAll(unAuthUsers);

        when(telegramUserUbiAccountEntryDatabaseService.findAllAuthorizationInfoForTelegram()).thenReturn(allUsers);

        List<UbiAccountAuthorizationEntryWithTelegram> result = telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertTrue(result.containsAll(unAuthUsers) && unAuthUsers.containsAll(result));

        verify(telegramUserUbiAccountEntryDatabaseService).findAllAuthorizationInfoForTelegram();

        verify(authorizationService, times(7)).authorizeAndGetDtoForEncodedPassword(any(), any());

        verify(telegramUserUbiAccountEntryDatabaseService, times(5)).saveAuthorizationInfo(any(), eq(authorizedEntry));
        verify(telegramUserUbiAccountEntryDatabaseService, times(0)).saveAuthorizationInfo(any(), eq(clientErrorEntry));
        verify(telegramUserUbiAccountEntryDatabaseService, times(0)).saveAuthorizationInfo(any(), eq(serverErrorEntry));
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
        user.setUbiTwoFactorAuthTicket(authorizationDTO.getTwoFactorAuthenticationTicket());
        return user;
    }
}