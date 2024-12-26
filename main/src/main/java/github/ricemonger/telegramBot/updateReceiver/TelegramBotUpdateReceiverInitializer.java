package github.ricemonger.telegramBot.updateReceiver;

import github.ricemonger.marketplace.services.configurations.TelegramBotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotUpdateReceiverInitializer extends TelegramBotsLongPollingApplication {

    @Autowired
    public TelegramBotUpdateReceiverInitializer(TelegramBotConfiguration telegramBotConfiguration,
                                                TelegramBotUpdateReceiver telegramBotUpdateReceiver) {

        String botToken = telegramBotConfiguration.getTELEGRAM_BOT_TOKEN();

        try {
            registerBot(botToken, telegramBotUpdateReceiver);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
