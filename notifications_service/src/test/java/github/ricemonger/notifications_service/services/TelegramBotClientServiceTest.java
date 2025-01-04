package github.ricemonger.notifications_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.verify;

@SpringBootTest
class TelegramBotClientServiceTest {
    @Autowired
    private TelegramBotClientService telegramBotClientService;
    @MockBean
    private TelegramBotClient telegramBotClient;

    @Test
    public void sendText_should_handle_to_bot_client() throws TelegramApiException {
        String chatId = "chatId";
        String message = "message";

        telegramBotClientService.sendText(chatId, message);

        verify(telegramBotClient).execute(new SendMessage(chatId, message));
    }
}