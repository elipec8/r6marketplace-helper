package github.ricemonger.telegramBot.client;

import github.ricemonger.telegramBot.UpdateInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TelegramBotClientServiceTests {

    @MockBean
    private TelegramBotClient telegramBotClient;

    @Autowired
    private TelegramBotClientService telegramBotClientService;

    @Test
    public void askFromInlineKeyboardShouldCreateKeyboardMarkupAndHandleItToClient() throws Exception{
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String text = "text";
        int buttonsInLine = 2;
        CallbackButton button1 = new CallbackButton("button1", "data1");
        CallbackButton button2 = new CallbackButton("button2", "data2");

        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, button1, button2);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), text);
        sendMessage.setReplyMarkup(createInlineKeyboardMarkup(buttonsInLine, button1, button2));

        verify(telegramBotClient).execute(sendMessage);
    }
    private InlineKeyboardMarkup createInlineKeyboardMarkup(int buttonsInLine,
                                                            CallbackButton... callbackButtons) {
        List<InlineKeyboardButton> inlineButtonsList = new ArrayList<>();
        List<InlineKeyboardRow> inlineRowsList = new ArrayList<>();

        int counter = 0;
        for (CallbackButton button : callbackButtons) {
            counter++;
            InlineKeyboardButton inlineButton = new InlineKeyboardButton(button.text());
            inlineButton.setCallbackData(button.data());
            inlineButtonsList.add(inlineButton);
            if (counter % buttonsInLine == 0) {
                inlineRowsList.add(new InlineKeyboardRow(inlineButtonsList));
                inlineButtonsList.clear();
            }
        }
        if (!inlineButtonsList.isEmpty()) {
            inlineRowsList.add(new InlineKeyboardRow(inlineButtonsList));
        }

        return new InlineKeyboardMarkup(inlineRowsList);
    }

    @Test
    public void askFromInlineKeyboardShouldThrowExceptionWhenClientThrowsException() throws Exception{

        doThrow(new TelegramApiException("message")).when(telegramBotClient).execute(any(SendMessage.class));

        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.askFromInlineKeyboard(new UpdateInfo(), "message", 1,
                new CallbackButton("text", "data")));
    }

    @Test
    public void sendTextShouldHandleMessageToClient() throws Exception{
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String answer = "answer";

        telegramBotClientService.sendText(updateInfo, answer);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), answer);

        verify(telegramBotClient).execute(sendMessage);
    }

    @Test
    public void sendTextShouldHandleMessageToClientWithChatIdAsParam() throws Exception{
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String answer = "answer";

        telegramBotClientService.sendText(String.valueOf(updateInfo.getChatId()), answer);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), answer);

        verify(telegramBotClient).execute(sendMessage);
    }

    @Test
    public void sendTextShouldThrowExceptionWhenClientThrowsException() throws Exception{
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), "message");

        doThrow(new TelegramApiException("message")).when(telegramBotClient).execute(sendMessage);

        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.sendText(updateInfo, "message"));
        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.sendText(String.valueOf(updateInfo.getChatId()), "message"));
    }

    @Test
    public void notifyUserAboutUbiAuthorizationFailureShouldHandleMessageToClient() throws Exception{
        String chatId = "chatId";
        String email = "email";

        telegramBotClientService.notifyUserAboutUbiAuthorizationFailure(chatId, email);

        verify(telegramBotClient).execute(any(SendMessage.class));
    }
}