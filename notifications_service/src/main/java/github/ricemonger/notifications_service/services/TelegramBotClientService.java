package github.ricemonger.notifications_service.services;

import github.ricemonger.utils.exceptions.server.TelegramApiRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramBotClientService {

    private final TelegramBotClient telegramBotClient;

    public void sendText(String chatId, String message) throws TelegramApiRuntimeException {
        SendMessage sendMessage = new SendMessage(chatId, message);
        executeMessageOnBot(sendMessage);
    }

    private void executeMessageOnBot(SendMessage sendMessage) throws TelegramApiRuntimeException {
        try {
            telegramBotClient.executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramApiRuntimeException(e);
        }
    }
}
