package github.ricemonger.notifications_service.services;

import github.ricemonger.notifications_service.services.configurations.TelegramBotConfiguration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;


@Component
public class TelegramBotClient extends OkHttpTelegramClient {

    public TelegramBotClient(TelegramBotConfiguration telegramBotConfiguration) {
        super(telegramBotConfiguration.getTELEGRAM_BOT_TOKEN());
    }
}
