package github.ricemonger.telegramBot.update_consumer;

import github.ricemonger.marketplace.services.configurations.TelegramBotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotUpdateConsumerInitializer extends TelegramBotsLongPollingApplication {

    @Autowired
    public TelegramBotUpdateConsumerInitializer(TelegramBotConfiguration telegramBotConfiguration,
                                                TelegramBotUpdateConsumer telegramBotUpdateConsumer) {

        String botToken = telegramBotConfiguration.getTELEGRAM_BOT_TOKEN();

        try {
            registerBot(botToken, telegramBotUpdateConsumer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
