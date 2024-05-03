package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.databases.neo4j.services.UserService;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BotInnerServiceTests {

    @MockBean
    public TelegramBotClientService telegramBotClientService;

    @MockBean
    public UserService userService;

    @Autowired
    public BotInnerService botInnerService;

    @Test
    public void askFromInlineKeyboardShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        String text = "text";
        int buttonsInLine = 1;
        CallbackButton[] buttons = new CallbackButton[1];

        botInnerService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);

        verify(telegramBotClientService).askFromInlineKeyboard(updateInfo, text, buttonsInLine, buttons);
    }

    @Test
    public void sendTextShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        String answer = "answer";

        botInnerService.sendText(updateInfo, answer);

        verify(telegramBotClientService).sendText(updateInfo, answer);
    }

    @Test
    public void isRegisteredShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.isRegistered(chatId);

        verify(userService).isTelegramUserRegistered(chatId);
    }

    @Test
    public void isRegisteredShouldReturnServiceAnswerIfTrue() {
        Long chatId = 1L;

        when(userService.isTelegramUserRegistered(chatId)).thenReturn(true);

        assertTrue(botInnerService.isRegistered(chatId));

        when(userService.isTelegramUserRegistered(chatId)).thenReturn(true);

        assertTrue(botInnerService.isRegistered(chatId));
    }

    @Test
    public void registerUserShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.registerUser(chatId);

        verify(userService).registerTelegramUser(chatId);
    }

    @Test
    public void addCredentialsFromUserInputsShouldHandleToServiceAndAddIfFull() {
        Long chatId = 1L;
        when(userService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email:password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(userService).addCredentials(chatId, "email", "password");

        verify(userService).clearUserInputs(chatId);
    }

    @Test
    public void addCredentialsFromUserInputsShouldHandleToServiceAndAddIfSeparated() {
        Long chatId = 1L;
        when(userService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("email");
        when(userService.getUserInputByState(chatId, InputState.CREDENTIALS_PASSWORD)).thenReturn("password");

        botInnerService.addCredentialsFromUserInputs(chatId);

        verify(userService).addCredentials(chatId, "email", "password");

        verify(userService).clearUserInputs(chatId);
    }

    @Test
    public void saveUserInputOrThrowShouldHandleToService() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("userInput");

        botInnerService.saveUserInputOrThrow(updateInfo);

        verify(userService).saveUserInput(updateInfo.getChatId(), updateInfo.getInputState(), "userInput");
    }

    @Test
    public void clearUserInputsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.clearUserInputs(chatId);

        verify(userService).clearUserInputs(chatId);
    }

    @Test
    public void setUserNextInputStateShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.setUserNextInputState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        verify(userService).setUserNextInput(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);
    }

    @Test
    public void setUserNextInputGroupShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);

        verify(userService).setUserNextInputGroup(chatId, InputGroup.CREDENTIALS_ADD);
    }

    @Test
    public void getUserInputByStateShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        verify(userService).getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);
    }

    @Test
    public void getUserInputByStateShouldReturnServiceAnswer() {
        Long chatId = 1L;

        when(userService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL)).thenReturn("userInput");

        assertEquals("userInput", botInnerService.getUserInputByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void removeCredentialsByUserInputsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.removeCredentialsByUserInputs(chatId);

        verify(userService).removeCredentialsByUserInputs(chatId);
    }

    @Test
    public void removeUserAllCredentialsShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.removeUserAllCredentials(chatId);

        verify(userService).removeAllCredentials(chatId);
    }

    @Test
    public void getCredentialsEmailsListShouldHandleToService() {
        Long chatId = 1L;

        botInnerService.getCredentialsEmailsList(chatId);

        verify(userService).getCredentialsEmailsList(chatId);
    }

    @Test
    public void getCredentialsEmailsListShouldReturnServiceAnswer() {
        Long chatId = 1L;

        when(userService.getCredentialsEmailsList(chatId)).thenReturn(List.of("email1", "email2"));

        assertEquals(List.of("email1", "email2"), botInnerService.getCredentialsEmailsList(chatId));
    }
}
