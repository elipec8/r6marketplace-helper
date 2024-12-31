package github.ricemonger.telegramBot.client;


import github.ricemonger.marketplace.services.*;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.ItemFilterFromInputsMapper;
import github.ricemonger.telegramBot.update_consumer.TradeManagerFromInputsMapper;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.server.InvalidTelegramUserInputException;
import github.ricemonger.utils.exceptions.server.MissingCallbackPrefixInUserInputException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BotInnerServiceTest {
    @SpyBean
    private BotInnerService botInnerService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private TelegramBotClientService telegramBotClientService;
    @MockBean
    private TelegramUserService telegramUserService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemFilterService itemFilterService;
    @MockBean
    private TradeManagerService tradeManagerService;
    @MockBean
    private ItemFilterFromInputsMapper itemFilterFromInputsMapper;
    @MockBean
    private TradeManagerFromInputsMapper tradeManagerFromInputsMapper;
    @MockBean
    private TagService tagService;

    @Test
    public void sendText_should_handle_to_service() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        botInnerService.sendText(updateInfo, text);

        verify(telegramBotClientService).sendText(same(updateInfo), same(text));
    }

    @Test
    public void sendText_should_throw_exception_if_service_throws() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";

        doThrow(new RuntimeException()).when(telegramBotClientService).sendText(same(updateInfo), same(text));

        assertThrows(RuntimeException.class, () -> botInnerService.sendText(updateInfo, text));
    }

    @Test
    public void askFromInlineKeyboard_should_handle_to_service() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        int buttonsInLine = 3;
        CallbackButton[] buttons = new CallbackButton[0];
        botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);

        verify(telegramBotClientService).askFromInlineKeyboard(same(updateInfo), same(text), same(buttonsInLine), same(buttons));
    }

    @Test
    public void askFromInlineKeyboard_should_throw_exception_if_service_throws() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        int buttonsInLine = 3;
        CallbackButton[] buttons = new CallbackButton[0];

        doThrow(new RuntimeException()).when(telegramBotClientService).askFromInlineKeyboard(same(updateInfo), same(text), same(buttonsInLine), same(buttons));

        assertThrows(RuntimeException.class, () -> botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons));
    }

    @Test
    public void sendMultipleObjectStringsGroupedInMessages_should_handle_to_service() {
        List objects = Mockito.mock(List.class);

        botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, 1, 2L);

        verify(telegramBotClientService).sendMultipleObjectStringsGroupedInMessages(same(objects), eq(1), eq(2L));
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_send_proper_amount_of_messages_if_few_in_message() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowFewInMessageFlag(true);
        itemShowSettings.setItemShowNameFlag(true);
        List<ItemFilter> appliedFilters = new ArrayList<>();
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(2);

        List<Item> itemMainFields = new ArrayList<>();

        itemMainFields.add(new Item());
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(itemService).getAllItemsShownFieldsByFilters(appliedFilters);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        itemMainFields.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        itemMainFields.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_send_proper_amount_of_messages_if_one_in_message() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowFewInMessageFlag(false);
        itemShowSettings.setItemShowNameFlag(true);
        List<ItemFilter> appliedFilters = new ArrayList<>();
        itemShowSettings.setItemShowAppliedFilters(new ArrayList<>());
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(2);

        List<Item> itemMainFields = new ArrayList<>();

        itemMainFields.add(new Item());
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(itemService).getAllItemsShownFieldsByFilters(appliedFilters);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        itemMainFields.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        itemMainFields.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(3);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(3)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(5);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(3)).sendText(eq("1"), anyString());
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_send_proper_amount_of_messages_if_invalid_offset() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowMessagesLimit(3);
        itemShowSettings.setItemShowFewInMessageFlag(false);
        itemShowSettings.setItemShowNameFlag(true);
        List<ItemFilter> appliedFilters = new ArrayList<>();
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        List<Item> itemMainFields = new ArrayList<>();
        itemMainFields.add(new Item());
        itemMainFields.add(new Item());
        itemMainFields.add(new Item());
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("invalid");
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(3)).sendText(eq("1"), anyString());

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenThrow(new TelegramUserInputDoesntExistException(""));
        reset(telegramBotClientService);
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(3)).sendText(eq("1"), anyString());
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_send_big_offset_message_if_offset_bigger_or_equals_items_size() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowMessagesLimit(3);
        List<ItemFilter> appliedFilters = new ArrayList<>();
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("100");

        List<Item> itemMainFields = new ArrayList<>();
        when(itemService.getAllItemsShownFieldsByFilters(appliedFilters)).thenReturn(new ArrayList(itemMainFields));

        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);

        verify(itemService).getAllItemsShownFieldsByFilters(appliedFilters);

        verify(telegramBotClientService, times(1)).sendText(eq("1"), eq("Too big offset or strict filters, no items to show"));

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        reset(telegramBotClientService);
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);

        verify(telegramBotClientService, times(1)).sendText(eq("1"), eq("Too big offset or strict filters, no items to show"));
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_throw_when_telegram_user_service_get_item_show_settings_throws() {
        when(telegramUserService.getItemShowSettings(1L)).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L));
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_throw_when_telegram_user_service_get_item_offset_by_user_input_throws_user_doesnt_exist() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L));
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_NOT_throw_when_telegram_user_service_get_item_offset_by_user_input_throws_input_doesnt_exist() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenThrow(new TelegramUserInputDoesntExistException(""));
        ItemShowSettings settings = new ItemShowSettings();
        settings.setItemShowMessagesLimit(6);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(settings);
        assertDoesNotThrow(() -> botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L));
    }

    @Test
    public void registerUser_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.registerUser(chatId);
        verify(telegramUserService).registerTelegramUser(chatId);
    }

    @Test
    public void registerUser_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).registerTelegramUser(chatId);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.registerUser(chatId));
    }

    @Test
    public void isUserRegistered_should_return_service_result() {
        long chatId = 1L;
        when(telegramUserService.isTelegramUserRegistered(chatId)).thenReturn(true);
        assertTrue(botInnerService.isUserRegistered(chatId));

        when(telegramUserService.isTelegramUserRegistered(chatId)).thenReturn(false);
        assertFalse(botInnerService.isUserRegistered(chatId));
    }

    @Test
    public void setUserInputState_should_handle_to_service() {
        long chatId = 1L;
        InputState inputState = InputState.ITEMS_SHOW_OFFSET;
        botInnerService.setUserInputState(chatId, inputState);
        verify(telegramUserService).setUserInputState(chatId, inputState);
    }

    @Test
    public void setUserInputState_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        InputState inputState = InputState.ITEMS_SHOW_OFFSET;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setUserInputState(chatId, inputState);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserInputState(chatId, inputState));
    }

    @Test
    public void setUserInputGroup_should_handle_to_service() {
        long chatId = 1L;
        InputGroup inputGroup = InputGroup.UBI_ACCOUNT_ENTRY_LINK;
        botInnerService.setUserInputGroup(chatId, inputGroup);
        verify(telegramUserService).setUserInputGroup(chatId, inputGroup);
    }

    @Test
    public void setUserInputGroup_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        InputGroup inputGroup = InputGroup.UBI_ACCOUNT_ENTRY_LINK;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setUserInputGroup(chatId, inputGroup);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserInputGroup(chatId, inputGroup));
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_get_both_email_and_password_from_two_inputs_if_conditions_doesnt_met() {
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL)).thenReturn("email");
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_PASSWORD)).thenReturn("password");
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE)).thenReturn("twoFaCode");

        botInnerService.addUserUbiAccountEntryByUserInput(1L);

        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_PASSWORD);
        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);

        verify(telegramUserService).authorizeAndSaveUbiUserByUserInput(1L, "email", "password", "twoFaCode");
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_throw_exception_if_service_throws() {
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL)).thenReturn("email");
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_PASSWORD)).thenReturn("password");
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE)).thenReturn("twoFaCode");

        botInnerService.addUserUbiAccountEntryByUserInput(1L);

        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_PASSWORD);
        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);

        doThrow(new RuntimeException("")).when(telegramUserService).authorizeAndSaveUbiUserByUserInput(1L, "email", "password", "twoFaCode");

        assertThrows(RuntimeException.class, () -> botInnerService.addUserUbiAccountEntryByUserInput(1L));
    }

    @Test
    public void reauthorizeUbiAccountEntryBy2FACode_should_handle_to_service() {
        String twoFaCode = "twoFaCode";
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE)).thenReturn(twoFaCode);
        botInnerService.reauthorizeUbiAccountEntryBy2FACode(1L);

        verify(telegramUserService).reauthorizeAndSaveExistingUbiUserBy2FACodeByUserInput(1L, twoFaCode);
    }

    @Test
    public void removeUserUbiAccountEntry_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.removeUserUbiAccountEntry(chatId);
        verify(telegramUserService).removeUbiUserByChatId(chatId);
    }

    @Test
    public void removeUserUbiAccountEntry_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).removeUbiUserByChatId(chatId);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.removeUserUbiAccountEntry(chatId));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_return_service_result() {
        String email = "Email";
        UbiAccountAuthorizationEntry entry = new UbiAccountAuthorizationEntry();
        entry.setEmail(email);
        when(telegramUserService.getUbiUserByChatId(1L)).thenReturn(entry);

        assertEquals(email, botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_throw_exception_if_user_doesnt_exist() {
        when(telegramUserService.getUbiUserByChatId(1L)).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_throw_exception_if_entry_doesnt_exist() {
        when(telegramUserService.getUbiUserByChatId(1L)).thenThrow(new UbiAccountEntryDoesntExistException(""));
        assertThrows(UbiAccountEntryDoesntExistException.class, () -> botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void saveUserInput_should_save_InputAndSet_input_State_from_message_text() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        updateInfo.setMessageText("text");
        updateInfo.setHasMessage(true);

        botInnerService.saveUserInput(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), updateInfo.getMessageText());
    }

    @Test
    public void saveUserInput_should_save_InputAndSet_input_State_from_callback_data() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        updateInfo.setCallbackQueryData("data");
        updateInfo.setHasCallBackQuery(true);

        botInnerService.saveUserInput(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), updateInfo.getCallbackQueryData());
    }

    @Test
    public void saveUserInputAndSetInput_State_should_throw_if_no_text_or_data_provided() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        assertThrows(InvalidTelegramUserInputException.class, () -> botInnerService.saveUserInput(updateInfo));
    }

    @Test
    public void saveUserInput_should_throw_if_user_InputAndSet_doesnt_existState() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.UBI_ACCOUNT_ENTRY_EMAIL);
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        updateInfo.setCallbackQueryData("data");
        updateInfo.setHasCallBackQuery(true);

        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).saveUserInput(any(), any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.saveUserInput(updateInfo));
    }

    @Test
    public void clearUserInputs_AndSetInputStateAndGroup_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.clearUserInputsAndSetInputStateAndGroup(chatId);
        verify(telegramUserService).clearUserInputsAndSetInputStateAndGroup(chatId);
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_existAndSetInputStateAndGroup() {
        long chatId = 1L;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).clearUserInputsAndSetInputStateAndGroup(chatId);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.clearUserInputsAndSetInputStateAndGroup(chatId));
    }

    @Test
    public void getStringOfAllTagsNamesByTagGroup_should_return_service_result() {
        when(tagService.getAllTags()).thenReturn(new ArrayList<>());

        assertEquals("", botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Rarity));

        Tag tag = new Tag();
        tag.setName("name");
        tag.setTagGroup(TagGroup.Rarity);
        when(tagService.getAllTags()).thenReturn(List.of(tag));

        assertNotEquals("", botInnerService.getStringOfAllTagsNamesByTagGroup(TagGroup.Rarity));
    }

    @Test
    public void saveUserItemFilterByUserInput_should_generate_item_filter_from_inputs_and_handle_to_service() {
        ItemFilter filter = new ItemFilter();
        when(botInnerService.generateItemFilterByUserInput(1L)).thenReturn(filter);

        botInnerService.saveUserItemFilterByUserInput(1L);

        verify(itemFilterService).save(eq("1"), same(filter));
    }

    @Test
    public void saveUserItemFilterByUserInput_should_throw_if_service_throws() {
        doThrow(new TelegramUserDoesntExistException("")).when(itemFilterService).save(any(), any());
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.saveUserItemFilterByUserInput(1L));
    }

    @Test
    public void saveUserItemFilterByUserInput_should_throw_if_generation_method_throws() {
        doThrow(new TelegramUserDoesntExistException("")).when(botInnerService).generateItemFilterByUserInput(any());
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.saveUserItemFilterByUserInput(1L));
    }

    @Test
    public void generateItemFilterByUserInput_should_return_filter_with_name_and_rarity() {
        List<TelegramUserInput> inputs = new ArrayList<>();
        ItemFilter filter = new ItemFilter();
        filter.setName("na");
        when(telegramUserService.getAllUserInputs(1L)).thenReturn(inputs);
        when(itemFilterFromInputsMapper.generateItemFilterByUserInput(inputs)).thenReturn(filter);

        assertEquals(filter.getName(), botInnerService.generateItemFilterByUserInput(1L).getName());

        verify(telegramUserService).getAllUserInputs(1L);

        verify(itemFilterFromInputsMapper).generateItemFilterByUserInput(inputs);
    }

    @Test
    public void generateItemFilterByUserInput_should_throw_if_user_doesnt_exist() {
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.generateItemFilterByUserInput(1L));
    }

    @Test
    public void getAllUserItemFilterNames_should_return_service_result() {
        List<String> names = new ArrayList<>();
        names.add("name");
        names.add("name2");

        when(itemFilterService.getAllNamesByChatId("1")).thenReturn(names);

        List<String> result = botInnerService.getAllUserItemFiltersNames(1L);

        assertTrue(result.containsAll(names) && names.containsAll(result));
    }

    @Test
    public void getAllUserItemFilterNames_should_throw_if_service_throws() {
        when(itemFilterService.getAllNamesByChatId("1")).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.getAllUserItemFiltersNames(1L));
    }

    @Test
    public void getUserItemFilterByUserCurrentInputCallbackFilterName_should_return_service_result() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");

        ItemFilter filter = new ItemFilter();
        filter.setMaxBuyPrice(1555);

        when(itemFilterService.getById("1", "filter_name")).thenReturn(filter);

        assertEquals(filter.getMaxBuyPrice(), botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(1L).getMaxBuyPrice());

        verify(telegramUserService).getUserInputByState(1L, InputState.ITEM_FILTER_NAME);

        verify(itemFilterService).getById("1", "filter_name");
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_Current_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.ITEM_FILTER_NAME);

        assertThrows(RuntimeException.class, () -> botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(1L));
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_filter_findCurrent() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(itemFilterService).getById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(1L));
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_callback_prefix_is_missing_in_Current_input() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn("filter_name");

        assertThrows(MissingCallbackPrefixInUserInputException.class, () -> botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");

        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L);

        verify(itemFilterService).deleteById("1", "filter_name");
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.ITEM_FILTER_NAME);

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_remove() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(itemFilterService).deleteById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_callback_prefix_in_input_is_missing() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn("filter_name");

        assertThrows(MissingCallbackPrefixInUserInputException.class, () -> botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void getUserItemShowSettings_should_return_service_result() {
        ItemShowSettings settings = new ItemShowSettings();
        settings.setItemShowMessagesLimit(5);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(settings);
        assertEquals(settings, botInnerService.getUserItemShowSettings(1L));
    }

    @Test
    public void getUserItemShowSettings_should_throw_if_service_throws() {
        when(telegramUserService.getItemShowSettings(1L)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> botInnerService.getUserItemShowSettings(1L));
    }

    @Test
    public void setUserItemShowSettingsFewItemsInMessageFlag_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(chatId, true);
        verify(telegramUserService).setItemShowFewItemsInMessageFlag(chatId, true);

        reset(telegramUserService);

        botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(chatId, false);
        verify(telegramUserService).setItemShowFewItemsInMessageFlag(chatId, false);
    }

    @Test
    public void setUserItemShowSettingsFewItemsInMessageFlag_should_throw_if_service_throws() {
        long chatId = 1L;
        doThrow(new RuntimeException()).when(telegramUserService).setItemShowFewItemsInMessageFlag(chatId, true);
        assertThrows(RuntimeException.class, () -> botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(chatId, true));
    }

    @Test
    public void setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput_should_handle_to_service() {
        int maximumMessageLimit = 10;
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("5");
        when(commonValuesService.getMaximumTelegramMessageLimit()).thenReturn(maximumMessageLimit);

        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimitByUserInput(1L, 5);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("-100");
        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimitByUserInput(1L, 1);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("invalid");
        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimitByUserInput(1L, maximumMessageLimit);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("100");
        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimitByUserInput(1L, maximumMessageLimit);

        reset(telegramUserService);

        doThrow(new TelegramUserInputDoesntExistException("")).when(telegramUserService).getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT);
        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimitByUserInput(1L, maximumMessageLimit);
    }

    @Test
    public void setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput_should_throw_if_service_throws_during_get_input() {
        long chatId = 1L;
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(chatId));
    }

    @Test
    public void setUserItemShowSettingsMessageLimitByUserInput_should_throw_if_service_throws_during_set_limitOrEdgeValue() {
        long chatId = 1L;
        doThrow(new RuntimeException()).when(telegramUserService).setItemShowMessagesLimitByUserInput(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(chatId));
    }

    @Test
    public void setUserItemShownFieldsSettingByUserInput_should_handle_to_service() {
        botInnerService.setUserItemShownFieldsSettingByUserInput(1L);

        verify(telegramUserService).setItemShownFieldsSettingsByUserInput(1L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }

    @Test
    public void setUserItemShownFieldsSettingByUserInput_should_throw_if_user_doesnt_exist() {
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setItemShownFieldsSettingsByUserInput(1L, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserItemShownFieldsSettingByUserInput(1L));
    }

    @Test
    public void setUserItemShownFieldsSettingByUserInput_should_throw_if_input_doesnt_exist() {
        doThrow(new TelegramUserInputDoesntExistException("")).when(telegramUserService).setItemShownFieldsSettingsByUserInput(
                1L,
                Callbacks.INPUT_CALLBACK_TRUE,
                Callbacks.INPUT_CALLBACK_FALSE);
        assertThrows(TelegramUserInputDoesntExistException.class, () -> botInnerService.setUserItemShownFieldsSettingByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_add_applied_filter_if_true_value_and_filter_is_not_added_yet() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowAppliedFilters(new ArrayList<>());
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn(Callbacks.INPUT_CALLBACK_TRUE);

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L);

        verify(telegramUserService).addItemShowAppliedFilter(1L, filter);
        verify(telegramUserService, times(0)).removeItemShowAppliedFilter(any(), any());
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_NOT_add_applied_filter_if_true_value_but_filter_is_already_added() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        List<ItemFilter> appliedFilters = new ArrayList<>();
        appliedFilters.add(filter);
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn(Callbacks.INPUT_CALLBACK_TRUE);

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L);

        verify(telegramUserService, times(0)).addItemShowAppliedFilter(any(), any());
        verify(telegramUserService, times(0)).removeItemShowAppliedFilter(any(), any());
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_remove_applied_filter_if_false_value_but_filter_is_already_added() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        List<ItemFilter> appliedFilters = new ArrayList<>();
        appliedFilters.add(filter);
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn("any_other");

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L);

        verify(telegramUserService, times(0)).addItemShowAppliedFilter(any(), any());
        verify(telegramUserService).removeItemShowAppliedFilter(1L, filterName);
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_NOT_remove_applied_filter_if_false_value_but_filter_is_not_added_yet() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowAppliedFilters(new ArrayList<>());
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn("any_value");

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L);

        verify(telegramUserService, times(0)).addItemShowAppliedFilter(any(), any());
        verify(telegramUserService, times(0)).removeItemShowAppliedFilter(any(), any());
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_add_filter() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowAppliedFilters(new ArrayList<>());
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn(Callbacks.INPUT_CALLBACK_TRUE);

        doThrow(new RuntimeException()).when(telegramUserService).addItemShowAppliedFilter(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_remove_filter() {
        String filterName = "filter_name";
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxBuyPrice(10);
        when(itemFilterService.getById("1", filterName)).thenReturn(filter);

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        List<ItemFilter> appliedFilters = new ArrayList<>();
        appliedFilters.add(filter);
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE)).thenReturn("any_other");

        doThrow(new RuntimeException()).when(telegramUserService).removeItemShowAppliedFilter(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_input_name_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.ITEM_FILTER_NAME);
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_input_add_or_remove_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }


    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_filter_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(itemFilterService).getById("1", "filter_name");
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_missing_callback_prefix() {
        when(telegramUserService.getUserInputByState(1L, InputState.ITEM_FILTER_NAME)).thenReturn("filter_name");
        assertThrows(MissingCallbackPrefixInUserInputException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_handle_to_service() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");
        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByItemIdManagerByUserInput(any(), eq(TradeOperationType.SELL), any(), eq(true))).thenReturn(tradeManager);

        botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL);

        verify(tradeManagerService).saveTradeByItemIdManager("1", tradeManager);
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).generateTradeByItemIdManagerByUserInput(any(), eq(TradeOperationType.SELL), any(),
                anyBoolean());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_save_manager() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");

        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByItemIdManagerByUserInput(any(), eq(TradeOperationType.SELL), any(), eq(true))).thenReturn(tradeManager);

        doThrow(new RuntimeException()).when(tradeManagerService).saveTradeByItemIdManager(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void saveUserTradeByFiltersManagerByUserInput_should_handle_to_service() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();

        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByFiltersManagerByUserInput(any(), anyInt(), any(), eq(true))).thenReturn(tradeManager);

        botInnerService.saveUserTradeByFiltersManagerByUserInput(1L);

        verify(tradeManagerService).saveTradeByFiltersManager("1", tradeManager);
    }

    @Test
    public void saveUserTradeByFiltersManagerByUserInput_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void saveUserTradeByFiltersManagerByUserInput_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).generateTradeByFiltersManagerByUserInput(any(), anyInt(), any(), anyBoolean());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void saveUserTradeByFiltersManagerByUserInput_should_throw_if_service_throws_during_save_manager() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();

        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByFiltersManagerByUserInput(any(), anyInt(), any(), eq(true))).thenReturn(tradeManager);

        doThrow(new RuntimeException()).when(tradeManagerService).saveTradeByFiltersManager(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_return_service_result() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");

        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByItemIdManagerByUserInput(any(), any(), any(), eq(true))).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).generateTradeByItemIdManagerByUserInput(any(), any(), any(), anyBoolean());
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeOperationType.SELL));
    }

    @Test
    public void generateTradeByFiltersManagerByUserInput_ItemId_should_return_service_result() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setAppliedFilters(new ArrayList<>());

        TradeManagersSettings settings = new TradeManagersSettings(true, true);

        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);

        when(tradeManagerFromInputsMapper.generateTradeByFiltersManagerByUserInput(any(), anyInt(), any(), eq(true))).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.generateTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void generateTradeByFiltersManagerByUserInput_ItemId_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void generateTradeByFiltersManagerByUserInput_ItemId_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).generateTradeByFiltersManagerByUserInput(any(), anyInt(), any(), anyBoolean());
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void getItemByUserInputItemId_should_return_service_result() {
        Item item = new Item();
        item.setName("item_name");

        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");
        when(itemService.getItemById("item_id")).thenReturn(item);

        assertTrue(botInnerService.getItemByUserInputItemId(1L).isFullyEquals(item));
    }

    @Test
    public void getItemByUserInputTradeByItemIdManagerEdit_should_throw_if_UserInput_item_service_throws() {
        doThrow(new RuntimeException()).when(itemService).getItemById(any());
        assertThrows(RuntimeException.class, () -> botInnerService.getItemByUserInputItemId(1L));
    }

    @Test
    public void getItemByUserInputTradeByItemIdManagerEdit_should_throw_if_user_service_throwsUserInput() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.getItemByUserInputItemId(1L));
    }

    @Test
    public void invertUserTradeByFiltersManagerEnabledByUserInput_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenReturn("name");

        botInnerService.invertUserTradeByFiltersManagerEnabledByUserInput(1L);

        verify(tradeManagerService).invertTradeByFiltersManagerEnabledFlagById("1", "name");
    }

    @Test
    public void invertUserTradeByFiltersManagerEnabledByUserInput_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        assertThrows(RuntimeException.class, () -> botInnerService.invertUserTradeByFiltersManagerEnabledByUserInput(1L));
    }

    @Test
    public void invertUserTradeByFiltersManagerEnabledByUserInput_should_throw_if_service_throws_during_inverse_operation() {
        doThrow(new RuntimeException()).when(tradeManagerService).invertTradeByFiltersManagerEnabledFlagById(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.invertUserTradeByFiltersManagerEnabledByUserInput(1L));
    }

    @Test
    public void invertUserTradeByItemIdManagerEnabledByUserInput_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");

        botInnerService.invertUserTradeByItemIdManagerEnabledByUserInput(1L);

        verify(tradeManagerService).invertTradeByItemIdManagerEnabledFlagById("1", "item_id");
    }

    @Test
    public void invertUserTradeByItemIdManagerEnabledByUserInput_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        assertThrows(RuntimeException.class, () -> botInnerService.invertUserTradeByItemIdManagerEnabledByUserInput(1L));
    }

    @Test
    public void invertUserTradeByItemIdManagerEnabledByUserInput_should_throw_if_service_throws_during_inverse_operation() {
        doThrow(new RuntimeException()).when(tradeManagerService).invertTradeByItemIdManagerEnabledFlagById(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.invertUserTradeByItemIdManagerEnabledByUserInput(1L));
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");

        botInnerService.removeUserTradeByItemIdManagerByUserInput(1L);

        verify(tradeManagerService).deleteTradeByItemIdManagerById("1", "item_id");
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_input_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByItemIdManagerByUserInput(1L));
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_remove() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");
        doThrow(new RuntimeException()).when(tradeManagerService).deleteTradeByItemIdManagerById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByItemIdManagerByUserInput(1L));
    }

    @Test
    public void removeUserTradeByFiltersManagerByUserInput_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenReturn("name");

        botInnerService.removeUserTradeByFiltersManagerByUserInput(1L);

        verify(tradeManagerService).deleteTradeByFiltersManagerById("1", "name");
    }

    @Test
    public void removeUserTradeByFiltersManagerByUserInput_should_throw_if_service_throws_during_input_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void removeUserTradeByFiltersManagerByUserInput_should_throw_if_service_throws_during_remove() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenReturn("name");
        doThrow(new RuntimeException()).when(tradeManagerService).deleteTradeByFiltersManagerById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByFiltersManagerByUserInput(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_return_service_result() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");
        tradeManager.setPriorityMultiplier(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");
        when(tradeManagerService.getTradeByItemIdManagerById("1", "item_id")).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_throw_if_service_throws_during_tradeManager_find() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");
        tradeManager.setPriorityMultiplier(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID)).thenReturn("item_id");

        doThrow(new RuntimeException()).when(tradeManagerService).getTradeByItemIdManagerById("1", "item_id");
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getUserTradeByFiltersManagerByUserInputItemId_should_return_service_result() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName("name");
        tradeManager.setPriorityMultiplier(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenReturn("name");
        when(tradeManagerService.getTradeByFiltersManagerById("1", "name")).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.getUserTradeByFiltersManagerByUserInputName(1L));
    }

    @Test
    public void getUserTradeByFiltersManagerByUserInputItemId_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME);
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByFiltersManagerByUserInputName(1L));
    }

    @Test
    public void getUserTradeByFiltersManagerByUserInputItemId_should_throw_if_service_throws_during_tradeManager_find() {
        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName("name");
        tradeManager.setPriorityMultiplier(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADE_BY_FILTERS_MANAGER_NAME)).thenReturn("name");

        doThrow(new RuntimeException()).when(tradeManagerService).getTradeByFiltersManagerById("1", "name");
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByFiltersManagerByUserInputName(1L));
    }

    @Test
    public void getAllUserTradeByItemIdManagers_should_return_service_result() {
        List<TradeByItemIdManager> tradeManagers = new ArrayList<>();
        tradeManagers.add(new TradeByItemIdManager());

        when(tradeManagerService.getAllTradeByItemIdManagersByChatId("1")).thenReturn(tradeManagers);

        assertEquals(tradeManagers, botInnerService.getAllUserTradeByItemIdManagers(1L));
    }

    @Test
    public void getAllUserTradeByItemIdManagers_should_throw_if_service_throws() {
        when(tradeManagerService.getAllTradeByItemIdManagersByChatId("1")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.getAllUserTradeByItemIdManagers(1L));
    }

    @Test
    public void getAllUserTradeByFiltersManagers_should_return_service_result() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();
        tradeManagers.add(new TradeByFiltersManager());

        when(tradeManagerService.getAllTradeByFiltersManagersByChatId("1")).thenReturn(tradeManagers);

        assertEquals(tradeManagers, botInnerService.getAllUserTradeByFiltersManagers(1L));
    }

    @Test
    public void getAllUserTradeByFiltersManagers_should_throw_if_service_throws() {
        when(tradeManagerService.getAllTradeByFiltersManagersByChatId("1")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.getAllUserTradeByFiltersManagers(1L));
    }

    @Test
    public void getUserTradeManagersSettings_should_return_service_result() {
        TradeManagersSettings settings = new TradeManagersSettings();
        settings.setNewManagersAreActiveFlag(true);
        settings.setManagingEnabledFlag(false);
        when(telegramUserService.getTradeManagersSettings(1L)).thenReturn(settings);
        assertEquals(settings, botInnerService.getUserTradeManagersSettings(1L));
    }

    @Test
    public void getUserTradeManagersSettings_should_throw_if_service_throws() {
        when(telegramUserService.getTradeManagersSettings(1L)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeManagersSettings(1L));
    }

    @Test
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_true_flag() {
        botInnerService.setUserTradeManagersSettingsNewManagersAreActiveFlag(1L, true);
        verify(telegramUserService).setTradeManagersSettingsNewManagersAreActiveFlag(1L, true);
    }

    @Test
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag_should_handle_to_service_false_flag() {
        botInnerService.setUserTradeManagersSettingsNewManagersAreActiveFlag(1L, false);
        verify(telegramUserService).setTradeManagersSettingsNewManagersAreActiveFlag(1L, false);
    }

    @Test
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag_should_throw_if_service_throws() {
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setTradeManagersSettingsNewManagersAreActiveFlag(1L, true);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserTradeManagersSettingsNewManagersAreActiveFlag(1L, true));
    }

    @Test
    public void setUserTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_true_flag() {
        botInnerService.setUserTradeManagersSettingsManagingEnabledFlag(1L, true);
        verify(telegramUserService).setTradeManagersSettingsManagingEnabledFlag(1L, true);
    }

    @Test
    public void setUserTradeManagersSettingsManagingEnabledFlag_should_handle_to_service_false_flag() {
        botInnerService.setUserTradeManagersSettingsManagingEnabledFlag(1L, false);
        verify(telegramUserService).setTradeManagersSettingsManagingEnabledFlag(1L, false);
    }

    @Test
    public void setUserTradeManagersSettingsManagingEnabledFlag_should_throw_if_service_throws() {
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setTradeManagersSettingsManagingEnabledFlag(1L, true);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserTradeManagersSettingsManagingEnabledFlag(1L, true));
    }

    @Test
    public void getUserInputByState_should_return_service_result() {
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL)).thenReturn("input");
        assertEquals("input", botInnerService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL));
    }

    @Test
    public void getUserInputByState_should_throw_if_service_throws() {
        when(telegramUserService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> botInnerService.getUserInputByState(1L, InputState.UBI_ACCOUNT_ENTRY_EMAIL));
    }
}
