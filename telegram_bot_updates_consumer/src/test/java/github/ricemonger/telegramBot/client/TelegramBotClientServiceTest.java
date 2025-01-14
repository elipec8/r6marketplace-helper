package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.exceptions.server.TelegramApiRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelegramBotClientServiceTest {
    @SpyBean
    private TelegramBotClientService telegramBotClientService;
    @MockBean
    private TelegramBotClient telegramBotClient;
    @MockBean
    private CommonValuesService commonValuesService;

    @Test
    public void deleteMessage_should_handle_delete_message_command_to_client() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        updateInfo.setMessageId(2);

        telegramBotClientService.deleteMessage(updateInfo);

        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(updateInfo.getChatId()), updateInfo.getMessageId());

        verify(telegramBotClient).executeAsync(deleteMessage);
    }

    @Test
    public void askFromInlineKeyboardShouldCreateKeyboardMarkupAndHandleItToClient() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String text = "text";
        int buttonsInLine = 2;
        CallbackButton button1 = new CallbackButton("button1", "data1");
        CallbackButton button2 = new CallbackButton("button2", "data2");

        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, button1, button2);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), text);
        sendMessage.setReplyMarkup(createInlineKeyboardMarkup(buttonsInLine, button1, button2));

        verify(telegramBotClient).executeAsync(sendMessage);
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
    public void askFromInlineKeyboardShouldThrowExceptionWhenClientThrowsException() throws Exception {

        doThrow(new TelegramApiException("message")).when(telegramBotClient).executeAsync(any(SendMessage.class));

        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.askFromInlineKeyboard(new UpdateInfo(), "message", 1,
                new CallbackButton("text", "data")));
    }

    @Test
    public void sendTextShouldHandleMessageToClient() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String answer = "answer";

        telegramBotClientService.sendText(updateInfo, answer);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), answer);

        verify(telegramBotClient).executeAsync(sendMessage);
    }

    @Test
    public void sendTextShouldHandleMessageToClientWithChatIdAsParam() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        String answer = "answer";

        telegramBotClientService.sendText(String.valueOf(updateInfo.getChatId()), answer);

        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), answer);

        verify(telegramBotClient).executeAsync(sendMessage);
    }

    @Test
    public void sendTextShouldThrowExceptionWhenClientThrowsException() throws Exception {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setChatId(1L);
        SendMessage sendMessage = new SendMessage(String.valueOf(updateInfo.getChatId()), "message");

        doThrow(new TelegramApiException("message")).when(telegramBotClient).executeAsync(sendMessage);

        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.sendText(updateInfo, "message"));
        assertThrows(TelegramApiRuntimeException.class, () -> telegramBotClientService.sendText(String.valueOf(updateInfo.getChatId()), "message"));
    }

    @Test
    public void sendMultipleObjectsStringsInMessage_should_send_proper_amount_of_messages() {
        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(3);

        int objectStringHeight = 2;
        long chatId = 1L;

        Collection<Object> objects = new ArrayList<>();

        telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(0)).sendText(anyString(), any());

        objects.add(new Object());
        telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(1)).sendText(anyString(), any());

        objects.add(new Object());
        reset(telegramBotClientService);
        telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
        verify(telegramBotClientService, times(2)).sendText(anyString(), any());

        when(commonValuesService.getMaximumTelegramMessageHeight()).thenReturn(2);
        objects.add(new Object());
        reset(telegramBotClientService);
        telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId);
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

        assertThrows(RuntimeException.class, () -> telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId));

        objects.add(new Object());
        assertThrows(RuntimeException.class, () -> telegramBotClientService.sendMultipleObjectStringsGroupedInMessages(objects, objectStringHeight, chatId));
    }
}