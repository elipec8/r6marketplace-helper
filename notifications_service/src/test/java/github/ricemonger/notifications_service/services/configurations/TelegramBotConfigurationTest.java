package github.ricemonger.notifications_service.services.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TelegramBotConfigurationTest {
    @Autowired
    TelegramBotConfiguration telegramBotConfiguration;

    @Test
    public void values_should_not_be_null() {
        assertNotNull(telegramBotConfiguration.getTELEGRAM_BOT_TOKEN());
        assertNotNull(telegramBotConfiguration.getTELEGRAM_BOT_USERNAME());
        assertNotNull(telegramBotConfiguration.getMessageHeight());
        assertNotNull(telegramBotConfiguration.getMessageLimit());
    }
}