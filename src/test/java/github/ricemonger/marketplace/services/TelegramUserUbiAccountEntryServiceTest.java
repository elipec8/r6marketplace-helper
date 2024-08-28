package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
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

        verify(telegramUserUbiAccountEntryDatabaseService).save("chatId", buildUbiAccount(email, encodedPassword, dto));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).save(any(), any());
        when(authorizationService.authorizeAndGetDTO(any(), any())).thenReturn(new AuthorizationDTO());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.authorizeAndSaveUser("chatId", "email", "password"));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_user_already_have_another_ubi_account_entry() {
        doThrow(UbiAccountEntryAlreadyExistsException.class).when(telegramUserUbiAccountEntryDatabaseService).save(any(), any());
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

        verify(telegramUserUbiAccountEntryDatabaseService).deleteByChatId("chatId");
    }

    @Test
    public void deleteByChatId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).deleteByChatId(any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteByChatId("chatId"));
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedList_should_reauthorize_all_users_and_return_unauthorized() {
        UbiAccountEntry authorizedEntry = new UbiAccountEntry();
        authorizedEntry.setEmail("email");
        authorizedEntry.setEncodedPassword("encodedPassword");

        UbiAccountEntry clientErrorEntry = new UbiAccountEntry();
        authorizedEntry.setEmail("email1");
        authorizedEntry.setEncodedPassword("encodedPassword1");

        UbiAccountEntry serverErrorEntry = new UbiAccountEntry();
        authorizedEntry.setEmail("email2");
        authorizedEntry.setEncodedPassword("encodedPassword2");

        List<UbiAccountWithTelegram> authUsers = new ArrayList<>();

        authUsers.add(new UbiAccountWithTelegram("1", authorizedEntry));
        authUsers.add(new UbiAccountWithTelegram("2", authorizedEntry));
        authUsers.add(new UbiAccountWithTelegram("3", authorizedEntry));

        List<UbiAccountWithTelegram> authUsersWithDatabaseExceptions = new ArrayList<>();

        authUsersWithDatabaseExceptions.add(new UbiAccountWithTelegram("6", authorizedEntry));
        authUsersWithDatabaseExceptions.add(new UbiAccountWithTelegram("7", authorizedEntry));

        when(authorizationService.authorizeAndGetDtoForEncodedPassword(authorizedEntry.getEmail(), authorizedEntry.getEncodedPassword())).thenReturn(new AuthorizationDTO());
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).save(eq("6"), eq(authorizedEntry));
        doThrow(UbiAccountEntryAlreadyExistsException.class).when(telegramUserUbiAccountEntryDatabaseService).save(eq("7"), eq(authorizedEntry));

        List<UbiAccountWithTelegram> unAuthUsers = new ArrayList<>();
        unAuthUsers.add(new UbiAccountWithTelegram("4", clientErrorEntry));
        unAuthUsers.add(new UbiAccountWithTelegram("5", serverErrorEntry));

        doThrow(UbiUserAuthorizationClientErrorException.class).when(authorizationService).authorizeAndGetDtoForEncodedPassword(clientErrorEntry.getEmail(), clientErrorEntry.getEncodedPassword());
        doThrow(UbiUserAuthorizationServerErrorException.class).when(authorizationService).authorizeAndGetDtoForEncodedPassword(serverErrorEntry.getEmail(), serverErrorEntry.getEncodedPassword());

        List<UbiAccountWithTelegram> allUsers = new ArrayList<>();
        allUsers.addAll(authUsers);
        allUsers.addAll(authUsersWithDatabaseExceptions);
        allUsers.addAll(unAuthUsers);

        when(telegramUserUbiAccountEntryDatabaseService.findAll()).thenReturn(allUsers);

        List<UbiAccountWithTelegram> result = telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertTrue(result.containsAll(unAuthUsers) && unAuthUsers.containsAll(result));

        verify(telegramUserUbiAccountEntryDatabaseService).findAll();

        verify(authorizationService, times(7)).authorizeAndGetDtoForEncodedPassword(any(), any());

        verify(telegramUserUbiAccountEntryDatabaseService, times(5)).save(any(), eq(authorizedEntry));
        verify(telegramUserUbiAccountEntryDatabaseService, times(0)).save(any(), eq(clientErrorEntry));
        verify(telegramUserUbiAccountEntryDatabaseService, times(0)).save(any(), eq(serverErrorEntry));
    }

    @Test
    public void findByChatId_should_return_ubi_account_entry() {
        String chatId = "chatId";
        UbiAccountEntry entry = new UbiAccountEntry();
        entry.setEmail("email");
        when(telegramUserUbiAccountEntryDatabaseService.findByChatId(chatId)).thenReturn(entry);

        assertEquals(entry, telegramUserUbiAccountEntryService.findByChatId(chatId));

        verify(telegramUserUbiAccountEntryDatabaseService).findByChatId(chatId);
    }

    @Test
    public void findByChatId_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).findByChatId(any());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId("chatId"));
    }

    @Test
    public void findByChatId_should_throw_if_ubi_account_entry_doesnt_exist() {
        doThrow(UbiAccountEntryDoesntExistException.class).when(telegramUserUbiAccountEntryDatabaseService).findByChatId(any());

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId("chatId"));
    }

    private UbiAccountEntry buildUbiAccount(String email, String password, AuthorizationDTO authorizationDTO) {
        UbiAccountEntry user = new UbiAccountEntry();
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