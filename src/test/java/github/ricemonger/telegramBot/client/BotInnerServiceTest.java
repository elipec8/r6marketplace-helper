package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.TelegramUserService;
import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.exceptions.InvalidTelegramUserInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BotInnerServiceTest {

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @MockBean
    private TelegramUserService telegramUserService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private BotInnerService botInnerService;

    @Test
    public void askFromInlineKeyboard_should_handle_to_client() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        int buttonsInLine = 1;
        CallbackButton[] buttons = new CallbackButton[1];

        botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);

        verify(telegramBotClientService).askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    @Test
    public void sendText_should_handle_to_client() {
        UpdateInfo updateInfo = new UpdateInfo();
        String answer = "answer";

        botInnerService.sendText(updateInfo, answer);

        verify(telegramBotClientService).sendText(updateInfo, answer);
    }

    @Test
    public void isRegistered_should_return_from_service() {
        Long chatId = 1L;

        when(telegramUserService.isTelegramUserRegistered(chatId)).thenReturn(true);

        assertTrue(botInnerService.isRegistered(chatId));

        when(telegramUserService.isTelegramUserRegistered(chatId)).thenReturn(false);

        assertFalse(botInnerService.isRegistered(chatId));
    }


    @Test
    public void registerUser_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.registerUser(chatId);

        verify(telegramUserService).registerTelegramUser(chatId);
    }

    @Test
    public void addCredentialsFromUserInputs_should_handle_to_service_and_add_if_full() {
        Long chatId = 1L;
        when(telegramUserService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(telegramUserService).addCredentialsIfValidOrThrowException(chatId, "email", "password");

        verify(telegramUserService).clearUserInputs(chatId);
    }

    @Test
    public void addCredentialsFromUserInputs_should_handle_to_service_and_add_if_separated() {
        Long chatId = 1L;
        when(telegramUserService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email");
        when(telegramUserService.getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD)).thenReturn("password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(telegramUserService).addCredentialsIfValidOrThrowException(chatId, "email", "password");

        verify(telegramUserService).clearUserInputs(chatId);
    }

    @Test
    public void removeCredentialsByUserInputs_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.removeCredentialsByUserInputs(chatId);

        verify(telegramUserService).removeCredentialsByUserInputs(chatId);
    }

    @Test
    public void saveUserInputOrThrow_should_handle_to_service_if_has_message() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("userInput");
        updateInfo.setHasCallBackQuery(false);

        botInnerService.saveUserInputOrThrow(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), "userInput");
    }

    @Test
    public void saveUserInputOrThrow_should_handle_to_service_if_has_callback_query() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(false);
        updateInfo.setHasCallBackQuery(true);
        updateInfo.setCallbackQueryData("userInput");

        botInnerService.saveUserInputOrThrow(updateInfo);

        verify(telegramUserService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), "userInput");
    }

    @Test
    public void saveUserInputOrThrow_should_throw_if_empty_input() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(false);
        updateInfo.setHasCallBackQuery(false);
        assertThrows(InvalidTelegramUserInput.class, () -> botInnerService.saveUserInputOrThrow(updateInfo));
    }

    @Test
    public void clearUserInputs_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.clearUserInputs(chatId);

        verify(telegramUserService).clearUserInputs(chatId);
    }

    @Test
    public void setUserNextInputState_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.setUserNextInputState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        verify(telegramUserService).setUserNextInputState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);
    }

    @Test
    public void setUserNextInputGroup_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);

        verify(telegramUserService).setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);
    }

    @Test
    public void getUserInputByState_should_handle_to_service() {
        Long chatId = 1L;

        when(telegramUserService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("userInput");

        assertEquals("userInput", botInnerService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void removeUserAllCredentials_should_handle_to_service() {
        Long chatId = 1L;

        botInnerService.removeUserAllCredentials(chatId);

        verify(telegramUserService).removeAllCredentials(chatId);
    }

    @Test
    public void getCredentialsEmailsList_should_handle_to_service() {
        Long chatId = 1L;

        List<String> expected = List.of("email1", "email2");
        when(telegramUserService.getCredentialsEmailsList(chatId)).thenReturn(expected);

        List<String> result = botInnerService.getCredentialsEmailsList(chatId);

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void sendDefaultSpeculativeItemsAsMessages_should_handle_to_service() {
        Long chatId = 1L;

        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());

        when(itemService.getAllSpeculativeItemsByExpectedProfit(50, 40, 0, 15000)).thenReturn(items);

        botInnerService.sendDefaultSpeculativeItemsAsMessages(chatId);

        verify(telegramBotClientService,times(2)).sendText(eq("1"),anyString());
    }
}
