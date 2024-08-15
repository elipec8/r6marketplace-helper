package github.ricemonger.telegramBot.client;


import github.ricemonger.marketplace.services.*;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.TagGroup;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import github.ricemonger.utils.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Collection;
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
    private ItemStatsService itemStatsService;
    @MockBean
    private TelegramUserItemFilterService telegramUserItemFilterService;
    @MockBean
    private TelegramUserTradeManagerService telegramUserTradeManagerService;
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
    public void sendMultipleObjectsStringsInMessage_should_send_proper_amount_of_messages() {
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(3);

        int objectStringHeight = 2;
        long chatId = 1L;

        Collection<Object> objects = new ArrayList<>();

        botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(0)).sendText(anyString(), any());

        objects.add(new Object());
        botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(1)).sendText(anyString(), any());

        objects.add(new Object());
        reset(telegramBotClientService);
        botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(2)).sendText(anyString(), any());

        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(2);
        objects.add(new Object());
        reset(telegramBotClientService);
        botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(3)).sendText(anyString(), any());
    }

    @Test
    public void sendMultipleObjectsStringsInMessage_should_throw_exception_if_service_throws() {
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(4);

        int objectStringHeight = 2;
        long chatId = 1L;

        Collection<Object> objects = new ArrayList<>();
        objects.add(new Object());

        doThrow(new RuntimeException()).when(telegramBotClientService).sendText(anyString(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId));

        objects.add(new Object());
        assertThrows(RuntimeException.class, () -> botInnerService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId));
    }

    @Test
    public void sendItemsByUserItemShowSettingsAndUserInputOffset_should_send_proper_amount_of_messages_if_few_in_message() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowFewInMessageFlag(true);
        itemShowSettings.setItemShowNameFlag(true);
        List<ItemFilter> appliedFilters = new ArrayList<>();
        itemShowSettings.setItemShowAppliedFilters(appliedFilters);
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(2);

        List<Item> items = new ArrayList<>();

        items.add(new Item());
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(itemStatsService).getAllItemsByFilters(appliedFilters);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        items.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        items.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
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

        List<Item> items = new ArrayList<>();

        items.add(new Item());
        itemShowSettings.setItemShowMessagesLimit(1);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(itemStatsService).getAllItemsByFilters(appliedFilters);
        verify(telegramBotClientService, times(1)).sendText(eq("1"), anyString());

        items.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        items.add(new Item());
        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(3);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("0");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(3)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(2);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);
        verify(telegramBotClientService, times(2)).sendText(eq("1"), anyString());

        reset(telegramBotClientService);
        itemShowSettings.setItemShowMessagesLimit(5);
        when(telegramUserService.getItemShowSettings(1L)).thenReturn(itemShowSettings);
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_OFFSET)).thenReturn("1");
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));
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

        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));

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

        List<Item> items = new ArrayList<>();
        when(itemStatsService.getAllItemsByFilters(appliedFilters)).thenReturn(new ArrayList(items));

        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(1L);

        verify(itemStatsService).getAllItemsByFilters(appliedFilters);

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
        InputGroup inputGroup = InputGroup.CREDENTIALS_ADD;
        botInnerService.setUserInputGroup(chatId, inputGroup);
        verify(telegramUserService).setUserInputGroup(chatId, inputGroup);
    }

    @Test
    public void setUserInputGroup_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        InputGroup inputGroup = InputGroup.CREDENTIALS_ADD;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).setUserInputGroup(chatId, inputGroup);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.setUserInputGroup(chatId, inputGroup));
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_get_both_email_and_password_from_one_input_if_conditions_are_met() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");

        botInnerService.addUserUbiAccountEntryByUserInput(1L);

        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL);
        verify(telegramUserService, times(0)).getUserInputByState(1L, InputState.CREDENTIALS_PASSWORD);

        verify(telegramUserService).addUserUbiAccountEntryIfValidCredentialsOrThrow(1L, "email", "password");
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_get_both_email_and_password_from_two_inputs_if_conditions_doesnt_met() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email");
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_PASSWORD)).thenReturn("password");

        botInnerService.addUserUbiAccountEntryByUserInput(1L);

        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL);
        verify(telegramUserService, times(1)).getUserInputByState(1L, InputState.CREDENTIALS_PASSWORD);

        verify(telegramUserService).addUserUbiAccountEntryIfValidCredentialsOrThrow(1L, "email", "password");
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_throw_exception_if_service_throws_user_doesnt_exist() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).addUserUbiAccountEntryIfValidCredentialsOrThrow(1L, "email", "password");

        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.addUserUbiAccountEntryByUserInput(1L));
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_throw_exception_if_service_throws_client_auth_error() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");
        doThrow(new UbiUserAuthorizationClientErrorException("")).when(telegramUserService).addUserUbiAccountEntryIfValidCredentialsOrThrow(1L, "email", "password");

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> botInnerService.addUserUbiAccountEntryByUserInput(1L));
    }

    @Test
    public void addUserUbiAccountEntryByUserInput_should_throw_exception_if_service_throws_server_auth_error() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");
        doThrow(new UbiUserAuthorizationServerErrorException("")).when(telegramUserService).addUserUbiAccountEntryIfValidCredentialsOrThrow(1L, "email", "password");

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> botInnerService.addUserUbiAccountEntryByUserInput(1L));
    }

    @Test
    public void removeUserUbiAccountEntry_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.removeUserUbiAccountEntry(chatId);
        verify(telegramUserService).removeUserUbiAccountEntry(chatId);
    }

    @Test
    public void removeUserUbiAccountEntry_should_throw_exception_if_service_throws() {
        long chatId = 1L;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).removeUserUbiAccountEntry(chatId);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.removeUserUbiAccountEntry(chatId));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_return_service_result() {
        String email = "Email";
        UbiAccountEntry entry = new UbiAccountEntry();
        entry.setEmail(email);
        when(telegramUserService.getUserUbiAccountEntry(1L)).thenReturn(entry);

        assertEquals(email, botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_throw_exception_if_user_doesnt_exist() {
        when(telegramUserService.getUserUbiAccountEntry(1L)).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void getUserUbiAccountEntryEmail_should_throw_exception_if_entry_doesnt_exist() {
        when(telegramUserService.getUserUbiAccountEntry(1L)).thenThrow(new UbiAccountEntryDoesntExistException(""));
        assertThrows(UbiAccountEntryDoesntExistException.class, () -> botInnerService.getUserUbiAccountEntryEmail(1L));
    }

    @Test
    public void saveUserInput_should_save_input_from_message_text() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setMessageText("text");
        updateInfo.setHasMessage(true);

        botInnerService.saveUserInput(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), updateInfo.getMessageText());
    }

    @Test
    public void saveUserInput_should_save_input_from_callback_data() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setCallbackQueryData("data");
        updateInfo.setHasCallBackQuery(true);

        botInnerService.saveUserInput(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), updateInfo.getCallbackQueryData());
    }

    @Test
    public void saveUserInput_should_throw_if_no_text_or_data_provided() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);

        assertThrows(InvalidTelegramUserInputException.class, () -> botInnerService.saveUserInput(updateInfo));
    }

    @Test
    public void saveUserInput_should_throw_if_user_doesnt_exist() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setCallbackQueryData("data");
        updateInfo.setHasCallBackQuery(true);

        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).saveUserInput(any(), any(), any());

        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.saveUserInput(updateInfo));
    }

    @Test
    public void clearUserInputs_should_handle_to_service() {
        long chatId = 1L;
        botInnerService.clearUserInputs(chatId);
        verify(telegramUserService).clearUserInputs(chatId);
    }

    @Test
    public void clearUserInputs_should_throw_if_user_doesnt_exist() {
        long chatId = 1L;
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserService).clearUserInputs(chatId);
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.clearUserInputs(chatId));
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

        verify(telegramUserItemFilterService).saveItemFilter(eq("1"), same(filter));
    }

    @Test
    public void saveUserItemFilterByUserInput_should_throw_if_service_throws() {
        doThrow(new TelegramUserDoesntExistException("")).when(telegramUserItemFilterService).saveItemFilter(any(), any());
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
        when(itemFilterFromInputsMapper.mapToItemFilter(inputs)).thenReturn(filter);

        assertEquals(filter.getName(), botInnerService.generateItemFilterByUserInput(1L).getName());

        verify(telegramUserService).getAllUserInputs(1L);

        verify(itemFilterFromInputsMapper).mapToItemFilter(inputs);
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

        when(telegramUserItemFilterService.getAllUserItemFiltersNames("1")).thenReturn(names);

        List<String> result = botInnerService.getAllUserItemFiltersNames(1L);

        assertTrue(result.containsAll(names) && names.containsAll(result));
    }

    @Test
    public void getAllUserItemFilterNames_should_throw_if_service_throws() {
        when(telegramUserItemFilterService.getAllUserItemFiltersNames("1")).thenThrow(new TelegramUserDoesntExistException(""));
        assertThrows(TelegramUserDoesntExistException.class, () -> botInnerService.getAllUserItemFiltersNames(1L));
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_return_service_result() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");

        ItemFilter filter = new ItemFilter();
        filter.setMaxPrice(1555);

        when(telegramUserItemFilterService.getItemFilterById("1", "filter_name")).thenReturn(filter);

        assertEquals(filter.getMaxPrice(), botInnerService.getUserItemFilterByUserInputCallbackFilterName(1L).getMaxPrice());

        verify(telegramUserService).getUserInputByState(1L, InputState.FILTER_NAME);

        verify(telegramUserItemFilterService).getItemFilterById("1", "filter_name");
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.FILTER_NAME);

        assertThrows(RuntimeException.class, () -> botInnerService.getUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_filter_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(telegramUserItemFilterService).getItemFilterById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.getUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void getUserItemFilterByUserInputCallbackFilterName_should_throw_if_callback_prefix_is_missing_in_input() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn("filter_name");

        assertThrows(MissingCallbackPrefixInUserInputException.class, () -> botInnerService.getUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");

        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L);

        verify(telegramUserItemFilterService).deleteItemFilterById("1", "filter_name");
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.FILTER_NAME);

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_service_throws_during_remove() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(telegramUserItemFilterService).deleteItemFilterById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserItemFilterByUserInputCallbackFilterName(1L));
    }

    @Test
    public void removeUserItemFilterByUserInputCallbackFilterName_should_throw_if_callback_prefix_in_input_is_missing() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn("filter_name");

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
    public void setUserItemShowSettingsMessageLimitByUserInput_should_handle_to_service() {
        int maximumMessageLimit = 10;
        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("5");
        when(commonValuesService.getMaximumTelegramMessageLimit()).thenReturn(maximumMessageLimit);

        botInnerService.setUserItemShowSettingsMessageLimitByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimit(1L, 5);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("-100");
        botInnerService.setUserItemShowSettingsMessageLimitByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimit(1L, 1);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("invalid");
        botInnerService.setUserItemShowSettingsMessageLimitByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimit(1L, maximumMessageLimit);

        reset(telegramUserService);

        when(telegramUserService.getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT)).thenReturn("100");
        botInnerService.setUserItemShowSettingsMessageLimitByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimit(1L, maximumMessageLimit);

        reset(telegramUserService);

        doThrow(new TelegramUserInputDoesntExistException("")).when(telegramUserService).getUserInputByState(1L, InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT);
        botInnerService.setUserItemShowSettingsMessageLimitByUserInput(1L);
        verify(telegramUserService).setItemShowMessagesLimit(1L, maximumMessageLimit);
    }

    @Test
    public void setUserItemShowSettingsMessageLimitByUserInput_should_throw_if_service_throws_during_get_input() {
        long chatId = 1L;
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.setUserItemShowSettingsMessageLimitByUserInput(chatId));
    }

    @Test
    public void setUserItemShowSettingsMessageLimitByUserInput_should_throw_if_service_throws_during_set_limit() {
        long chatId = 1L;
        doThrow(new RuntimeException()).when(telegramUserService).setItemShowMessagesLimit(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.setUserItemShowSettingsMessageLimitByUserInput(chatId));
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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + filterName);

        ItemFilter filter = new ItemFilter();
        filter.setName(filterName);
        filter.setMaxPrice(10);
        when(telegramUserItemFilterService.getItemFilterById("1", filterName)).thenReturn(filter);

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
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.FILTER_NAME);
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_input_add_or_remove_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }


    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_service_throws_during_filter_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn(Callbacks.INPUT_CALLBACK_PREFIX + "filter_name");
        doThrow(new RuntimeException()).when(telegramUserItemFilterService).getItemFilterById("1", "filter_name");
        assertThrows(RuntimeException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void updateUserItemShowAppliedFiltersSettingsByUserInput_should_throw_if_missing_callback_prefix() {
        when(telegramUserService.getUserInputByState(1L, InputState.FILTER_NAME)).thenReturn("filter_name");
        assertThrows(MissingCallbackPrefixInUserInputException.class, () -> botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(1L));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_handle_to_service() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");

        when(tradeManagerFromInputsMapper.mapToTradeManagerByItemId(any(), eq(TradeManagerTradeType.SELL), any())).thenReturn(tradeManager);

        botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL);

        verify(telegramUserTradeManagerService).saveUserTradeByItemIdManager("1", tradeManager);
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).mapToTradeManagerByItemId(any(), eq(TradeManagerTradeType.SELL), any());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void saveUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_save_manager() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");

        when(tradeManagerFromInputsMapper.mapToTradeManagerByItemId(any(), eq(TradeManagerTradeType.SELL), any())).thenReturn(tradeManager);

        doThrow(new RuntimeException()).when(telegramUserTradeManagerService).saveUserTradeByItemIdManager(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.saveUserTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_return_service_result() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");

        when(tradeManagerFromInputsMapper.mapToTradeManagerByItemId(any(), any(), any())).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_throw_if_service_throws_during_inputs_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getAllUserInputs(1L);
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void generateTradeByItemIdManagerByUserInput_ItemId_should_throw_if_mapper_throws_during_mapping() {
        doThrow(new RuntimeException()).when(tradeManagerFromInputsMapper).mapToTradeManagerByItemId(any(), any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.generateTradeByItemIdManagerByUserInput(1L, TradeManagerTradeType.SELL));
    }

    @Test
    public void getItemByUserInputTradeByItemIdManagerEdit_should_return_service_result() {
        Item item = new Item();
        item.setName("item_name");

        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenReturn("item_id");
        when(itemStatsService.getItemById("item_id")).thenReturn(item);

        assertEquals(item, botInnerService.getItemByUserInputTradeByItemIdManagerEdit(1L));
    }

    @Test
    public void getItemByUserInputTradeByItemIdManagerEdit_should_throw_if_item_service_throws() {
        doThrow(new RuntimeException()).when(itemStatsService).getItemById(any());
        assertThrows(RuntimeException.class, () -> botInnerService.getItemByUserInputTradeByItemIdManagerEdit(1L));
    }

    @Test
    public void getItemByUserInputTradeByItemIdManagerEdit_should_throw_if_user_service_throws() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(any(), any());
        assertThrows(RuntimeException.class, () -> botInnerService.getItemByUserInputTradeByItemIdManagerEdit(1L));
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_handle_to_service() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenReturn("item_id");

        botInnerService.removeUserTradeByItemIdManagerByUserInput(1L);

        verify(telegramUserTradeManagerService).deleteUserTradeByItemIdManagerById("1", "item_id");
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_input_find() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByItemIdManagerByUserInput(1L));
    }

    @Test
    public void removeUserTradeByItemIdManagerByUserInput_should_throw_if_service_throws_during_remove() {
        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenReturn("item_id");
        doThrow(new RuntimeException()).when(telegramUserTradeManagerService).deleteUserTradeByItemIdManagerById(any(), any());

        assertThrows(RuntimeException.class, () -> botInnerService.removeUserTradeByItemIdManagerByUserInput(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_return_service_result() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");
        tradeManager.setPriority(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenReturn("item_id");
        when(telegramUserTradeManagerService.getUserTradeByItemIdManagerById("1", "item_id")).thenReturn(tradeManager);

        assertEquals(tradeManager, botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_throw_if_service_throws_during_input_find() {
        doThrow(new RuntimeException()).when(telegramUserService).getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID);
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getUserTradeByItemIdManagerByUserInputItemId_should_throw_if_service_throws_during_tradeManager_find() {
        TradeByItemIdManager tradeManager = new TradeByItemIdManager();
        tradeManager.setItemId("item_id");
        tradeManager.setPriority(100);
        when(telegramUserService.getUserInputByState(1L, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID)).thenReturn("item_id");

        doThrow(new RuntimeException()).when(telegramUserTradeManagerService).getUserTradeByItemIdManagerById("1", "item_id");
        assertThrows(RuntimeException.class, () -> botInnerService.getUserTradeByItemIdManagerByUserInputItemId(1L));
    }

    @Test
    public void getAllUserTradeByItemIdManagers_should_return_service_result() {
        List<TradeByItemIdManager> tradeManagers = new ArrayList<>();
        tradeManagers.add(new TradeByItemIdManager());

        when(telegramUserTradeManagerService.getAllUserTradeByItemIdManagers("1")).thenReturn(tradeManagers);

        assertEquals(tradeManagers, botInnerService.getAllUserTradeByItemIdManagers(1L));
    }

    @Test
    public void getAllUserTradeByItemIdManagers_should_throw_if_service_throws() {
        when(telegramUserTradeManagerService.getAllUserTradeByItemIdManagers("1")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.getAllUserTradeByItemIdManagers(1L));
    }

    @Test
    public void getAllUserTradeByFiltersManagers_should_return_service_result() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();
        tradeManagers.add(new TradeByFiltersManager());

        when(telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("1")).thenReturn(tradeManagers);

        assertEquals(tradeManagers, botInnerService.getAllUserTradeByFiltersManagers(1L));
    }

    @Test
    public void getAllUserTradeByFiltersManagers_should_throw_if_service_throws() {
        when(telegramUserTradeManagerService.getAllUserTradeByFiltersManagers("1")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> botInnerService.getAllUserTradeByFiltersManagers(1L));
    }

    @Test
    public void getUserInputByState_should_return_service_result() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("input");
        assertEquals("input", botInnerService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByState_should_throw_if_service_throws() {
        when(telegramUserService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> botInnerService.getUserInputByState(1L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }
}
