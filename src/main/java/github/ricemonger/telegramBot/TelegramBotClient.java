package github.ricemonger.telegramBot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

public class TelegramBotClient extends OkHttpTelegramClient {
    public TelegramBotClient(String botToken) {
        super(botToken);
    }
}
