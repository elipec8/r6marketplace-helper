package github.ricemonger.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotInitializer extends TelegramBotsLongPollingApplication {

    @Autowired
    public TelegramBotInitializer(TelegramBotConfiguration telegramBotConfiguration,
                                  TelegramBotUpdateReceiver telegramBotUpdateReceiver) {

        String botToken = telegramBotConfiguration.getTELEGRAM_BOT_TOKEN();

        try {
            registerBot(botToken, telegramBotUpdateReceiver);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public TelegramBotClient telegramBotClient(TelegramBotConfiguration telegramBotConfiguration) {
        return new TelegramBotClient(telegramBotConfiguration.getTELEGRAM_BOT_TOKEN());
    }
}
