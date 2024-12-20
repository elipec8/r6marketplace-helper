package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
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
    @Autowired
    private TelegramUserService telegramUserService;
    @MockBean
    private TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryDatabaseService;
    @MockBean
    private TelegramUserDatabaseService telegramUserDatabaseService;
    @MockBean
    private TelegramUserInputDatabaseService telegramUserInputDatabaseService;

    @Test
    public void registerTelegramUser_should_handle_to_service() {
        telegramUserService.registerTelegramUser(123L);
        verify(telegramUserDatabaseService).create("123");
    }

    @Test
    public void setUserInputState_should_save_state() {
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(new TelegramUser(123L));

        telegramUserService.setUserInputState(123L, InputState.UBI_ACCOUNT_ENTRY_EMAIL);

        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);

        verify(telegramUserDatabaseService).update(telegramUser);
    }

    @Test
    public void setUserInputState_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserInputState(123L, InputState.BASE));
    }

    @Test
    public void setUserInputGroup_should_save_group() {
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(new TelegramUser(123L));

        telegramUserService.setUserInputGroup(123L, InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        verify(telegramUserDatabaseService).update(telegramUser);
    }

    @Test
    public void setUserInputGroup_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserInputGroup(123L, InputGroup.BASE));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_handle_to_service() {
        telegramUserService.setItemShowFewItemsInMessageFlag(123L, true);
        verify(telegramUserDatabaseService).setItemShowFewItemsInMessageFlag("123", true);

        telegramUserService.setItemShowFewItemsInMessageFlag(223L, false);
        verify(telegramUserDatabaseService).setItemShowFewItemsInMessageFlag("223", false);
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setItemShowFewItemsInMessageFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowFewItemsInMessageFlag(123L, true));
    }

    @Test
    public void setItemShowMessagesLimit_OrEdgeValue_should_handle_to_service() {
        telegramUserService.setItemShowMessagesLimit(123L, 5);
        verify(telegramUserDatabaseService).setItemShowMessagesLimit("123", 5);
    }

    @Test
    public void setItemShowMessagesLimit_OrEdgeValue_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setItemShowMessagesLimit("123", 5);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowMessagesLimit(123L, 5));
    }

    @Test
    public void setItemShownFieldsSettingsByUserInput_should_parse_and_handle_to_service() {
        String chatId = "123";

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME, Callbacks.INPUT_CALLBACK_TRUE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_TYPE, Callbacks.INPUT_CALLBACK_TRUE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MAX_BUY_PRICE, Callbacks.INPUT_CALLBACK_TRUE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_BUY_ORDERS_COUNT, Callbacks.INPUT_CALLBACK_TRUE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE, Callbacks.INPUT_CALLBACK_FALSE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT, Callbacks.INPUT_CALLBACK_FALSE));

        when(telegramUserInputDatabaseService.findById(chatId, InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE)).thenReturn(new TelegramUserInput(chatId,
                InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE, Callbacks.INPUT_CALLBACK_FALSE));

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings(true, true, true, true, false, false, false);

        telegramUserService.setItemShownFieldsSettingsByUserInput(123L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);

        verify(telegramUserDatabaseService).setItemShowFieldsSettings(chatId, settings);
    }

    @Test
    public void setItemShownFieldsSettingsByUserInput_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShownFieldsSettingsByUserInput(123L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE));
    }

    @Test
    public void addItemShowAppliedFilter_should_handle_to_service() {
        ItemFilter filter = new ItemFilter();
        telegramUserService.addItemShowAppliedFilter(123L, filter);
        verify(telegramUserDatabaseService).addItemShowAppliedFilter(eq("123"), same(filter));
    }

    @Test
    public void addItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).addItemShowAppliedFilter("123", new ItemFilter());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addItemShowAppliedFilter(123L, new ItemFilter()));
    }

    @Test
    public void removeItemShowAppliedFilter_should_handle_to_service() {
        telegramUserService.removeItemShowAppliedFilter(123L, "filterName");
        verify(telegramUserDatabaseService).removeItemShowAppliedFilter("123", "filterName");
    }

    @Test
    public void removeItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).removeItemShowAppliedFilter("123", "filterName");

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeItemShowAppliedFilter(123L, "filterName"));
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_create_and_authorize_user_and_clear_user_inputsCredentials() {
        telegramUserService.authorizeAndSaveUser(123L, "email", "password", "twoFactorCode");

        verify(telegramUserInputDatabaseService).deleteAllByChatId("123");

        verify(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUser("123", "email", "password", "twoFactorCode");
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_user_doesnt_existCredentials() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.authorizeAndSaveUser(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_invalid_user_credentials() {
        doThrow(UbiUserAuthorizationClientErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUser("123", "email", "password", "twoFactorCode");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserService.authorizeAndSaveUser(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_ubiAccountEntry_server_errorCredentials() {
        doThrow(UbiUserAuthorizationServerErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUser("123", "email", "password", "twoFactorCode");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserService.authorizeAndSaveUser(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void removeUserUbiAccountEntry_should_handle_to_service() {
        telegramUserService.removeUserUbiAccountEntry(123L);

        verify(telegramUserUbiAccountEntryDatabaseService).deleteByChatId("123");
    }

    @Test
    public void removeUserUbiAccountEntry_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeUserUbiAccountEntry(123L));
    }

    @Test
    public void saveUserInput_should_handle_to_service() {
        telegramUserService.saveUserInput(123L, InputState.BASE, "input");

        verify(telegramUserInputDatabaseService).save("123", InputState.BASE, "input");
    }

    @Test
    public void saveUserInput_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.saveUserInput(123L, InputState.BASE, "input"));
    }

    @Test
    public void clearUserInputs_should_handle_to_service() {
        telegramUserService.clearUserInputs(123L);

        verify(telegramUserInputDatabaseService).deleteAllByChatId("123");
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.clearUserInputs(123L));
    }

    @Test
    public void isTelegramUserRegistered_should_return_result_from_database_service() {
        when(telegramUserDatabaseService.existsById("123")).thenReturn(true);

        assertTrue(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserDatabaseService).existsById("123");

        reset(telegramUserDatabaseService);

        when(telegramUserDatabaseService.existsById("123")).thenReturn(false);

        assertFalse(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserDatabaseService).existsById("123");
    }

    @Test
    public void getTelegramUser_should_return_user() {
        TelegramUser telegramUser = new TelegramUser(123L);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(telegramUser, telegramUserService.getTelegramUser(123L));

        verify(telegramUserDatabaseService).findUserById("123");
    }

    @Test
    public void getTelegramUser_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTelegramUser(123L));
    }

    @Test
    public void getItemShowSettings_should_return_settings() {
        ItemShowSettings settings = new ItemShowSettings();
        when(telegramUserDatabaseService.findUserItemShowSettingsById("123")).thenReturn(settings);

        assertEquals(settings, telegramUserService.getItemShowSettings(123L));

        verify(telegramUserDatabaseService).findUserItemShowSettingsById("123");
    }

    @Test
    public void getItemShowSettings_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserItemShowSettingsById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getItemShowSettings(123L));
    }

    @Test
    public void getTradeManagersSettings_should_return_settings() {
        TradeManagersSettings settings = new TradeManagersSettings();
        settings.setNewManagersAreActiveFlag(true);
        settings.setManagingEnabledFlag(false);
        when(telegramUserDatabaseService.findUserTradeManagersSettingsById("123")).thenReturn(settings);

        assertEquals(settings, telegramUserService.getTradeManagersSettings(123L));

        verify(telegramUserDatabaseService).findUserTradeManagersSettingsById("123");
    }

    @Test
    public void getTradeManagersSettings_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserTradeManagersSettingsById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTradeManagersSettings(123L));
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_true_flag() {
        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, true);
        verify(telegramUserDatabaseService).setTradeManagersSettingsNewManagersAreActiveFlag("123", true);
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_false_flag() {
        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, false);
        verify(telegramUserDatabaseService).setTradeManagersSettingsNewManagersAreActiveFlag("123", false);
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_throw_if_service_throws() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setTradeManagersSettingsNewManagersAreActiveFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, true));
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_true_flag() {
        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, true);
        verify(telegramUserDatabaseService).setTradeManagersSettingsManagingEnabledFlag("123", true);
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_false_flag() {
        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, false);
        verify(telegramUserDatabaseService).setTradeManagersSettingsManagingEnabledFlag("123", false);
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_throw_if_service_throws() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setTradeManagersSettingsManagingEnabledFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, true));
    }

    @Test
    public void getUserInputState_should_return_state() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputState.UBI_ACCOUNT_ENTRY_EMAIL, telegramUserService.getUserInputState(123L));

        verify(telegramUserDatabaseService).findUserById("123");
    }

    @Test
    public void getUserInputState_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroup_should_return_group() {
        TelegramUser telegramUser = new TelegramUser(123L);
        telegramUser.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        when(telegramUserDatabaseService.findUserById("123")).thenReturn(telegramUser);

        assertEquals(InputGroup.UBI_ACCOUNT_ENTRY_LINK, telegramUserService.getUserInputGroup(123L));

        verify(telegramUserDatabaseService).findUserById("123");
    }

    @Test
    public void getUserInputGroup_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserUbiAccountEntry_should_return_user_ubi_account_entry() {
        when(telegramUserUbiAccountEntryDatabaseService.findByChatId("123")).thenReturn(new UbiAccountAuthorizationEntry());

        assertEquals(new UbiAccountAuthorizationEntry(), telegramUserService.getUserUbiAccountEntry(123L));

        verify(telegramUserUbiAccountEntryDatabaseService).findByChatId("123");
    }

    @Test
    public void getUserUbiAccountEntry_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserUbiAccountEntry(123L));
    }

    @Test
    public void getUserUbiAccountEntry_should_throw_if_entry_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(UbiAccountEntryDoesntExistException.class);

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserService.getUserUbiAccountEntry(123L));
    }

    @Test
    public void getUserInputByState_should_return_input() {
        TelegramUserInput telegramUserInput = new TelegramUserInput("123", InputState.BASE, "input");
        when(telegramUserInputDatabaseService.findById("123", InputState.BASE)).thenReturn(telegramUserInput);

        assertEquals("input", telegramUserService.getUserInputByState(123L, InputState.BASE));

        verify(telegramUserInputDatabaseService).findById("123", InputState.BASE);
    }

    @Test
    public void getUserInputByState_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getUserInputByState_should_throw_if_input_doesnt_exist() {
        when(telegramUserInputDatabaseService.findById("123", InputState.BASE)).thenThrow(TelegramUserInputDoesntExistException.class);

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserService.getUserInputByState(123L, InputState.BASE));
    }

    @Test
    public void getAllUserInputs_should_return_all_user_inputs() {
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput("123", InputState.BASE, "value"));

        when(telegramUserInputDatabaseService.findAllByChatId("123")).thenReturn(inputs);

        assertEquals(inputs, telegramUserService.getAllUserInputs(123L));

        verify(telegramUserInputDatabaseService).findAllByChatId("123");
    }

    @Test
    public void getAllUserInputs_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getAllUserInputs(123L));
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
}