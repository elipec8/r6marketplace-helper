package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.DTOs.*;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
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
    private UbiAccountEntryService telegramUserUbiAccountEntryDatabaseService;
    @MockBean
    private TelegramUserDatabaseService telegramUserDatabaseService;
    @MockBean
    private TelegramUserInputDatabaseService telegramUserInputDatabaseService;

    @Test
    public void registerTelegramUser_should_handle_to_service() {
        telegramUserService.registerTelegramUser(123L);
        verify(telegramUserDatabaseService).register("123");
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
        verify(telegramUserDatabaseService).setUserItemShowFewItemsInMessageFlag("123", true);

        telegramUserService.setItemShowFewItemsInMessageFlag(223L, false);
        verify(telegramUserDatabaseService).setUserItemShowFewItemsInMessageFlag("223", false);
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setUserItemShowFewItemsInMessageFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowFewItemsInMessageFlag(123L, true));
    }

    @Test
    public void setItemShowMessagesLimit_OrEdgeValue_should_handle_to_service() {
        telegramUserService.setItemShowMessagesLimitByUserInput(123L, 5);
        verify(telegramUserDatabaseService).setUserItemShowMessagesLimit("123", 5);
    }

    @Test
    public void setItemShowMessagesLimit_OrEdgeValue_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setUserItemShowMessagesLimit("123", 5);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowMessagesLimitByUserInput(123L, 5));
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

        verify(telegramUserDatabaseService).setUserItemShowFieldsSettings(chatId, settings);
    }

    @Test
    public void setItemShownFieldsSettingsByUserInput_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setUserItemShowFieldsSettings(eq("123"), any());

        when(telegramUserInputDatabaseService.findById(any(), any())).thenReturn(new TelegramUserInput());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShownFieldsSettingsByUserInput(123L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE));
    }

    @Test
    public void addItemShowAppliedFilter_should_handle_to_service() {
        ItemFilter filter = new ItemFilter();
        telegramUserService.addItemShowAppliedFilter(123L, filter);
        verify(telegramUserDatabaseService).addUserItemShowAppliedFilter(eq("123"), same(filter));
    }

    @Test
    public void addItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).addUserItemShowAppliedFilter("123", new ItemFilter());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addItemShowAppliedFilter(123L, new ItemFilter()));
    }

    @Test
    public void removeItemShowAppliedFilter_should_handle_to_service() {
        telegramUserService.removeItemShowAppliedFilter(123L, "filterName");
        verify(telegramUserDatabaseService).removeUserItemShowAppliedFilter("123", "filterName");
    }

    @Test
    public void removeItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).removeUserItemShowAppliedFilter("123", "filterName");

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeItemShowAppliedFilter(123L, "filterName"));
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_create_and_authorize_user_and_clear_UserBy_user_Input_inputsCredentials() {
        telegramUserService.authorizeAndSaveUbiUserByUserInput(123L, "email", "password", "twoFactorCode");

        verify(telegramUserInputDatabaseService).deleteAllByChatId("123");

        verify(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUbiAccountEntry("123", "email", "password", "twoFactorCode");
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_UserBy_user_Input_doesnt_existCredentials() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.authorizeAndSaveUbiUserByUserInput(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void authorizeAndSaveUserUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_invalid_UserBy_user_Input_credentials() {
        doThrow(UbiUserAuthorizationClientErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUbiAccountEntry("123", "email", "password", "twoFactorCode");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserService.authorizeAndSaveUbiUserByUserInput(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void authorizeAndSaveUbiUserByUserInputUbiAccountEntryIfValidCredentialsOrThrow_should_throw_if_ubiAccountEntry_server_errorCredentials() {
        doThrow(UbiUserAuthorizationServerErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).authorizeAndSaveUbiAccountEntry("123", "email", "password", "twoFactorCode");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserService.authorizeAndSaveUbiUserByUserInput(123L, "email", "password", "twoFactorCode"));
    }

    @Test
    public void reauthorizeAndSaveExistingUserBy2FACode_should_create_and_authorize_user_and_clear_user_inputsCredentials() {
        telegramUserService.reauthorizeAndSaveExistingUbiUserBy2FACodeByUserInput(123L, "twoFactorCode");

        verify(telegramUserInputDatabaseService).deleteAllByChatId("123");

        verify(telegramUserUbiAccountEntryDatabaseService).reauthorizeAndSaveExistingUbiAccountEntryBy2FACode("123", "twoFactorCode");
    }

    @Test
    public void reauthorizeAndSaveExistingUserBy2FACode_should_throw_if_user_doesnt_existCredentials() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.reauthorizeAndSaveExistingUbiUserBy2FACodeByUserInput(123L, "twoFactorCode"));
    }

    @Test
    public void reauthorizeAndSaveExistingUserBy2FACode_should_throw_if_invalid_user_credentials() {
        doThrow(UbiUserAuthorizationClientErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).reauthorizeAndSaveExistingUbiAccountEntryBy2FACode("123", "twoFactorCode");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUserService.reauthorizeAndSaveExistingUbiUserBy2FACodeByUserInput(123L, "twoFactorCode"));
    }

    @Test
    public void reauthorizeAndSaveExistingUserBy2FACode_should_throw_if_ubiAccountEntry_server_errorCredentials() {
        doThrow(UbiUserAuthorizationServerErrorException.class).when(telegramUserUbiAccountEntryDatabaseService).reauthorizeAndSaveExistingUbiAccountEntryBy2FACode("123", "twoFactorCode");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUserService.reauthorizeAndSaveExistingUbiUserBy2FACodeByUserInput(123L, "twoFactorCode"));
    }

    @Test
    public void removeUbiUserByChatId_should_handle_to_service() {
        telegramUserService.removeUbiUserByChatId(123L);

        verify(telegramUserUbiAccountEntryDatabaseService).deleteByChatId("123");
    }

    @Test
    public void removeUserUbiAccountEntry_should_throw_if__doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeUbiUserByChatId(123L));
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
    public void clearUserInputs_AndSetInputStateAndGroup_should_handle_to_service() {
        telegramUserService.clearUserInputsAndSetInputStateAndGroup(123L);

        verify(telegramUserInputDatabaseService).deleteAllByChatId("123");
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_existAndSetInputStateAndGroup() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.clearUserInputsAndSetInputStateAndGroup(123L));
    }

    @Test
    public void isTelegramUserRegistered_should_return_result_from_database_service() {
        when(telegramUserDatabaseService.isRegistered("123")).thenReturn(true);

        assertTrue(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserDatabaseService).isRegistered("123");

        reset(telegramUserDatabaseService);

        when(telegramUserDatabaseService.isRegistered("123")).thenReturn(false);

        assertFalse(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserDatabaseService).isRegistered("123");
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
    public void getTelegramUserInputStateAndGroup_should_return_service_result() {
        when(telegramUserDatabaseService.findUserInputStateAndGroupById("123")).thenReturn(new TelegramUserInputStateAndGroup("chatId", InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID, InputGroup.ITEM_FILTER_EDIT));

        assertEquals(new TelegramUserInputStateAndGroup("chatId", InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID, InputGroup.ITEM_FILTER_EDIT), telegramUserService.getTelegramUserInputStateAndGroup(123L));
    }

    @Test
    public void getTelegramUserInputStateAndGroup_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserInputStateAndGroupById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTelegramUserInputStateAndGroup(123L));
    }

    @Test
    public void getItemShowSettings_should_return_settings() {
        ItemShowSettings settings = new ItemShowSettings();
        when(telegramUserDatabaseService.findUserItemShowSettings("123")).thenReturn(settings);

        assertEquals(settings, telegramUserService.getItemShowSettings(123L));

        verify(telegramUserDatabaseService).findUserItemShowSettings("123");
    }

    @Test
    public void getItemShowSettings_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserItemShowSettings("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getItemShowSettings(123L));
    }

    @Test
    public void getTradeManagersSettings_should_return_settings() {
        TradeManagersSettings settings = new TradeManagersSettings();
        settings.setNewManagersAreActiveFlag(true);
        settings.setManagingEnabledFlag(false);
        when(telegramUserDatabaseService.findUserTradeManagersSettings("123")).thenReturn(settings);

        assertEquals(settings, telegramUserService.getTradeManagersSettings(123L));

        verify(telegramUserDatabaseService).findUserTradeManagersSettings("123");
    }

    @Test
    public void getTradeManagersSettings_should_throw_if_user_doesnt_exist() {
        when(telegramUserDatabaseService.findUserTradeManagersSettings("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getTradeManagersSettings(123L));
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_true_flag() {
        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, true);
        verify(telegramUserDatabaseService).setUserTradeManagersSettingsNewManagersAreActiveFlag("123", true);
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_false_flag() {
        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, false);
        verify(telegramUserDatabaseService).setUserTradeManagersSettingsNewManagersAreActiveFlag("123", false);
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_throw_if_service_throws() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setUserTradeManagersSettingsNewManagersAreActiveFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(123L, true));
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_true_flag() {
        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, true);
        verify(telegramUserDatabaseService).setUserTradeManagersSettingsManagingEnabledFlag("123", true);
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_false_flag() {
        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, false);
        verify(telegramUserDatabaseService).setUserTradeManagersSettingsManagingEnabledFlag("123", false);
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_throw_if_service_throws() {
        doThrow(TelegramUserDoesntExistException.class).when(telegramUserDatabaseService).setUserTradeManagersSettingsManagingEnabledFlag("123", true);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsManagingEnabledFlag(123L, true));
    }

    @Test
    public void getUserUbiAccountEntry_should_return__ubi_userByChatId() {
        when(telegramUserUbiAccountEntryDatabaseService.findByChatId("123")).thenReturn(new UbiAccountAuthorizationEntry());

        assertEquals(new UbiAccountAuthorizationEntry(), telegramUserService.getUbiUserByChatId(123L));

        verify(telegramUserUbiAccountEntryDatabaseService).findByChatId("123");
    }

    @Test
    public void getUserUbiAccountEntry_should_throw_if__doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUbiUserByChatId(123L));
    }

    @Test
    public void getUbiUserByChatId_doesnt_exist() {
        when(telegramUserDatabaseService.findUserById("123")).thenThrow(UbiAccountEntryDoesntExistException.class);

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserService.getUbiUserByChatId(123L));
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
        when(telegramUserInputDatabaseService.findById("123", InputState.BASE)).thenThrow(TelegramUserDoesntExistException.class);

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
        when(telegramUserInputDatabaseService.findAllByChatId("123")).thenThrow(TelegramUserDoesntExistException.class);

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getAllUserInputs(123L));
    }
}