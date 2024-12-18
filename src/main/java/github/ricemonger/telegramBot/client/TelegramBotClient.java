package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.configurations.TelegramBotConfiguration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBotClient extends OkHttpTelegramClient {

    public TelegramBotClient(TelegramBotConfiguration telegramBotConfiguration) {
        super(telegramBotConfiguration.getTELEGRAM_BOT_TOKEN());

        try {
            this.execute(new SetMyCommands(PublicBotCommands.botCommands));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
