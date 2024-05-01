package github.ricemonger.telegramBot.client;

import github.ricemonger.telegramBot.UpdateInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.*;
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
        String text = "text";
        int buttonsInLine = 2;
        CallbackButton button1 = new CallbackButton("button1", "data1");
        CallbackButton button2 = new CallbackButton("button2", "data2");

        telegramBotClientService.askFromInlineKeyboard(updateInfo, text, buttonsInLine, button1, button2);

        SendMessage sendMessage = new SendMessage(text, String.valueOf(updateInfo.getChatId()));

        verify(telegramBotClient).execute(sendMessage);
    }

}