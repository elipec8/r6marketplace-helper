package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramUserServiceTest {

    @MockBean
    private TelegramUserDatabaseService userService;

    @MockBean
    private TelegramUserInputDatabaseService inputService;

    @MockBean
    private TelegramUbiAccountService credentialsService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Test
    public void isTelegramUserRegistered_should_Return_result_from_database_service() {
        when(userService.existsById("123")).thenReturn(true);

        assertTrue(telegramUserService.isTelegramUserRegistered(123L));

        when(userService.existsById("123")).thenReturn(false);

        assertFalse(telegramUserService.isTelegramUserRegistered(123L));
    }

    @Test
    public void registerTelegramUser_should_throw_exception_when_user_WithDefaultSettings_already_exists() {
        when(userService.existsById("123")).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramUserService.registerTelegramUserWithDefaultSettings(123L));
    }

    @Test
    public void registerTelegramUser_should_save_user_when_user_WithDefaultSettings_doesnt_exist() {
        when(userService.existsById("123")).thenReturn(false);

        telegramUserService.registerTelegramUserWithDefaultSettings(123L);

        verify(userService).update(new TelegramUser(123L));
    }

    @Test
    public void setUserNextInputState_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserNextInputState(123L, InputState.BASE));
    }

    @Test
    public void setUserNextInputState_should_save_state() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.BASE);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.setUserNextInputState(123L, InputState.BASE);

        verify(userService).update(telegramUser);
    }

    @Test
    public void setUserNextInputGroup_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserNextInputGroup(123L, InputGroup.BASE));
    }

    @Test
    public void setUserNextInputGroup_should_save_group() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.BASE);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.setUserNextInputGroup(123L, InputGroup.BASE);

        verify(userService).update(telegramUser);
    }

    @Test
    public void getUserInputState_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputState_should_return_state() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.BASE);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputState.BASE, telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroup_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroup_should_return_group() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.BASE);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputGroup.BASE, telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputByStateOrNull_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getUserInputByStateOrNull_should_throw_if_input_doesnt_exist() {
        when(inputService.findById("123", InputState.BASE)).thenThrow(TelegramUserInputDoesntExistException.class);

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getUserInputByStateOrNull_should_return_input() {
        TelegramUserInput telegramUserInput = new TelegramUserInput("123", InputState.BASE, "input");
        when(inputService.findById("123", InputState.BASE)).thenReturn(telegramUserInput);

        assertEquals("input", telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void saveUserInput_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.saveUserInput(123L, InputState.BASE, "input"));
    }

    @Test
    public void saveUserInput_should_save_input() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.saveUserInput(123L, InputState.BASE, "input");

        verify(inputService).save("123", InputState.BASE, "input");
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.clearUserInputs(123L));
    }

    @Test
    public void clearUserInputs_should_delete_all_inputs() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.clearUserInputs(123L);

        verify(inputService).deleteAllByChatId("123");
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_invalid_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        doThrow(UbiUserAuthorizationClientErrorException.class).when(credentialsService).authorizeAndSaveUser("123", "email", "password");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_throw_if_server_error() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        doThrow(UbiUserAuthorizationServerErrorException.class).when(credentialsService).authorizeAndSaveUser("123", "email", "password");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email",
                "password"));
    }

    @Test
    public void addCredentialsIfValidOrThrowException_should_create_and_authorize_user() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password");

        verify(credentialsService).authorizeAndSaveUser("123", "email", "password");
    }

    @Test
    public void removeCredentialsByUserInputs_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputs_should_throw_if_input_doesnt_exist() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        when(inputService.findById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenThrow(TelegramUserInputDoesntExistException.class);

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputs_should_remove_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        when(inputService.findById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn(new TelegramUserInput("123",
                InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));

        telegramUserService.removeCredentialsByUserInputs(123L);

        //    verify(credentialsService).deleteByChatId("123", "email");
    }

    @Test
    public void removeCredentialsByUserInputs_should_clear_user_inputs() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        when(inputService.findById("123", InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn(new TelegramUserInput("123",
                InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));

        telegramUserService.removeCredentialsByUserInputs(123L);

        verify(inputService).deleteAllByChatId("123");
    }

    @Test
    public void removeAllCredentials_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeAllCredentials(123L));
    }

    @Test
    public void removeAllCredentials_should_remove_all_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        telegramUserService.removeAllCredentials(123L);

        verify(credentialsService).deleteByChatId("123");
    }

    @Test
    public void getCredentialsEmailsList_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getCredentialsEmailsList(123L));
    }

    @Test
    public void getCredentialsEmailsList_should_return_credentials_list() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        List<UbiAccount> list = new ArrayList<>();
        UbiAccount user1 = new UbiAccount();
        user1.setEmail("email1");
        UbiAccount user2 = new UbiAccount();
        user2.setEmail("email2");
        list.add(user1);
        list.add(user2);

        // when(credentialsService.findByChatId("123")).thenReturn(list);

        List<String> expected = List.of("email1", "email2");
        // List<String> result = telegramUserService.getCredentialsEmailsList(123L);

        //   assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void getCredentialsEmailsList_should_return_empty_list_if_no_credentials() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        //  when(credentialsService.findByChatId("123")).thenReturn(Collections.emptyList());

        //   List<String> result = telegramUserService.getCredentialsEmailsList(123L);

        //  assertTrue(result.isEmpty());
    }

    @Test
    public void getAllChatIdsForNotifiableUsers_should_return_only_notifiable_users() {
        TelegramUser user1 = new TelegramUser(1L);
        user1.setPublicNotificationsEnabledFlag(true);
        TelegramUser user2 = new TelegramUser(2L);
        user2.setPublicNotificationsEnabledFlag(false);
        TelegramUser user3 = new TelegramUser(3L);
        user3.setPublicNotificationsEnabledFlag(true);

        when(userService.findAllUsers()).thenReturn(List.of(user1, user2, user3));

        List<String> result = telegramUserService.getAllChatIdsForNotifiableUsers();

        assertTrue(List.of("1", "3").containsAll(result) && result.containsAll(List.of("1", "3")));
    }

    @Test
    public void getTelegramUser_should_throw_if_user_doesnt_exist() {
        when(userService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTelegramUser(123L));
    }

    @Test
    public void getTelegramUser_should_return_user() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(userService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(telegramUser, telegramUserService.getTelegramUser(123L));
    }

    @Test
    public void getItemOffsetByUserInput_should_return_offset() {
        when(inputService.findById("123", InputState.ITEMS_SHOW_OFFSET)).thenReturn(new TelegramUserInput("123", InputState.ITEMS_SHOW_OFFSET, "5"));

        assertEquals(5, telegramUserService.getItemOffsetByUserInput(123L));
    }

    @Test
    public void getItemShowSettings_should_return_settings() {
        ItemShowSettings settings = new ItemShowSettings();
        when(userService.findUserSettingsById("123")).thenReturn(settings);

        assertEquals(settings, telegramUserService.getItemShowSettings(123L));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_handle_to_service() {
        telegramUserService.setItemShowFewItemsInMessageFlag(123L, true);
        verify(userService).setItemShowFewItemsInMessageFlag("123", true);
    }

    @Test
    public void setItemShowMessagesLimit_should_handle_to_service() {
        telegramUserService.setItemShowMessagesLimit(123L, 5);
        verify(userService).setItemShowMessagesLimit("123", 5);
    }

    @Test
    public void setItemShowSettingsByUserInput_should_parse_and_call_service() {
        String chatId = "123";

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME, Callbacks.INPUT_CALLBACK_TRUE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE, Callbacks.INPUT_CALLBACK_TRUE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE, Callbacks.INPUT_CALLBACK_TRUE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT, Callbacks.INPUT_CALLBACK_TRUE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE, Callbacks.INPUT_CALLBACK_FALSE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT, Callbacks.INPUT_CALLBACK_FALSE));

        when(inputService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE, Callbacks.INPUT_CALLBACK_FALSE));

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings(true, true, true, true, false, false, false);

        telegramUserService.setItemShowSettingsByUserInput(123L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);

        verify(userService).setItemShowFieldsSettings(chatId, settings);
    }

    @Test
    public void addItemShowAppliedFilter_should_handle_to_service() {
        ItemFilter filter = new ItemFilter();
        telegramUserService.addItemShowAppliedFilter(123L, filter);
        verify(userService).addItemShowAppliedFilter("123", filter);
    }

    @Test
    public void removeItemShowAppliedFilter_should_handle_to_service() {
        telegramUserService.removeItemShowAppliedFilter(123L, "filterName");
        verify(userService).removeItemShowAppliedFilter("123", "filterName");
    }
}