package github.ricemonger.telegramBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;

@SpringBootTest
public class TelegramBotUpdateReceiverTests {

    @Autowired
    private TelegramBotUpdateReceiver telegramBotUpdateReceiver;

    @Test
    public void testConsume() {
        telegramBotUpdateReceiver.consume(new Update());
    }
}
