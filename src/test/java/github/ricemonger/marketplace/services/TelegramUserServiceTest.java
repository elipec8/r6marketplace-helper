package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramUserServiceTest {

    @MockBean
    private TelegramUserDatabaseService telegramUserDatabaseService;

    @MockBean
    private UbiUserService ubiUserService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Test
    public void isTelegramUserRegistered_should_Return_result_from_database_service() {
        when(telegramUserDatabaseService.userExistsById("123")).thenReturn(true);

        assertTrue(telegramUserService.isTelegramUserRegistered(123L));

        when(telegramUserDatabaseService.userExistsById("123")).thenReturn(false);

        assertFalse(telegramUserService.isTelegramUserRegistered(123L));
    }

    @Test
    public void registerTelegramUser_should_throw_exception_when_user_already_exists() {
        when(telegramUserDatabaseService.userExistsById("123")).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramUserService.registerTelegramUser(123L));
    }

    @Test
    public void registerTelegramUser_should_save_user_when_user_doesnt_exist() {
        when(telegramUserDatabaseService.userExistsById("123")).thenReturn(false);

        telegramUserService.registerTelegramUser(123L);

        verify(telegramUserDatabaseService).saveUser(new TelegramUser(123L));
    }

    @Test
    public void setUserNextInputState_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserNextInputState(123L, InputState.BASE));
    }

    @Test
    public void setUserNextInputState_should_save_state() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.BASE);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.setUserNextInputState(123L, InputState.BASE);

        verify(telegramUserDatabaseService).saveUser(telegramUser);
    }

    @Test
    public void setUserNextInputGroup_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserNextInputGroup(123L, InputGroup.BASE));
    }

    @Test
    public void setUserNextInputGroup_should_save_group() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.BASE);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.setUserNextInputGroup(123L, InputGroup.BASE);

        verify(telegramUserDatabaseService).saveUser(telegramUser);
    }

    @Test
    public void getUserInputState_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputState_should_return_state() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.BASE);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputState.BASE, telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroup_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroup_should_return_group() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.BASE);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputGroup.BASE, telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputByStateOrNull_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getUserInputByStateOrNull_should_throw_if_input_doesnt_exist() {
        when(telegramUserDatabaseService.findInputById("123", InputState.BASE)).thenThrow(TelegramUserInputDoesntExistException.class);

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getUserInputByStateOrNull_should_return_input() {
        TelegramUserInput telegramUserInput = new TelegramUserInput("123", InputState.BASE, "input");
        when(telegramUserDatabaseService.findInputById("123", InputState.BASE)).thenReturn(telegramUserInput);

        assertEquals("input", telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void saveUserInput_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.saveUserInput(123L, InputState.BASE, "input"));
    }

    @Test
    public void saveUserInput_should_save_input() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.saveUserInput(123L, InputState.BASE, "input");

        verify(telegramUserDatabaseService).saveInput("123", InputState.BASE, "input");
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.clearUserInputs(123L));
    }

    @Test
    public void clearUserInputs_should_delete_all_inputs() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.clearUserInputs(123L);

        verify(telegramUserDatabaseService).deleteAllInputsByChatId("123");
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_invalid_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        doThrow(UbiUserAuthorizationClientErrorException.class).when(ubiUserService).authorizeAndSaveUser("123", "email", "password");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_server_error() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        doThrow(UbiUserAuthorizationServerErrorException.class).when(ubiUserService).authorizeAndSaveUser("123", "email", "password");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email",
                "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_create_and_authorize_user() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password");

        verify(ubiUserService).authorizeAndSaveUser("123", "email", "password");
    }

    @Test
    public void removeCredentialsByUserInputs_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputs_should_throw_if_input_doesnt_exist() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        when(telegramUserDatabaseService.findInputById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenThrow(TelegramUserInputDoesntExistException.class);

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputs_should_remove_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        when(telegramUserDatabaseService.findInputById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn(new TelegramUserInput("123", InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));

        telegramUserService.removeCredentialsByUserInputs(123L);

        verify(ubiUserService).deleteByLinkedTelegramUserChatIdAndEmail("123","email");
    }

    @Test
    public void removeCredentialsByUserInputs_should_clear_user_inputs() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        when(telegramUserDatabaseService.findInputById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn(new TelegramUserInput("123", InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));

        telegramUserService.removeCredentialsByUserInputs(123L);

        verify(telegramUserDatabaseService).deleteAllInputsByChatId("123");
    }

    @Test
    public void removeAllCredentials_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeAllCredentials(123L));
    }

    @Test
    public void removeAllCredentials_should_remove_all_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.removeAllCredentials(123L);

        verify(ubiUserService).deleteAllByLinkedTelegramUserChatId("123");
    }

    @Test
    public void getCredentialsEmailsList_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getCredentialsEmailsList(123L));
    }

    @Test
    public void getCredentialsEmailsList_should_return_credentials_list() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        List<UbiUser> list = new ArrayList<>();
        UbiUser user1 = new UbiUser();
        user1.setEmail("email1");
        UbiUser user2 = new UbiUser();
        user2.setEmail("email2");
        list.add(user1);
        list.add(user2);

        when(ubiUserService.findAllByLinkedTelegramUserChatId("123")).thenReturn(list);

        List<String> expected = List.of("email1", "email2");
        List<String> result = telegramUserService.getCredentialsEmailsList(123L);

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void getCredentialsEmailsList_should_return_empty_list_if_no_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        when(ubiUserService.findAllByLinkedTelegramUserChatId("123")).thenReturn(Collections.emptyList());

        List<String> result = telegramUserService.getCredentialsEmailsList(123L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllChatIdsForNotifiableUsers_should_return_only_notifiable_users() {
        TelegramUser user1 = new TelegramUser(1L);
        user1.setPublicNotificationsEnabledFlag(true);
        TelegramUser user2 = new TelegramUser(2L);
        user2.setPublicNotificationsEnabledFlag(false);
        TelegramUser user3 = new TelegramUser(3L);
        user3.setPublicNotificationsEnabledFlag(true);

        when(telegramUserDatabaseService.findAllUsers()).thenReturn(List.of(user1, user2, user3));

        List<String> result = telegramUserService.getAllChatIdsForNotifiableUsers();

        assertTrue(List.of("1", "3").containsAll(result) && result.containsAll(List.of("1", "3")));
    }

    @Test
    public void getTelegramUser_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTelegramUser(123L));
    }

    @Test
    public void getTelegramUser_should_return_user() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(telegramUser, telegramUserService.getTelegramUser(123L));
    }
}