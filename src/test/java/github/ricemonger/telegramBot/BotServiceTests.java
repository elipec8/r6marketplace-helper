package github.ricemonger.telegramBot;

import github.ricemonger.marketplace.databases.neo4j.services.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BotServiceTests {

    @MockBean
    private TelegramUserService telegramUserService;

    @Autowired
    private BotService botService;

    @Test
    void notifyAllUsersAboutItemAmountIncreaseShouldCallTelegramUserService() {
        botService.notifyAllUsersAboutItemAmountIncrease(1, 2);

        verify(telegramUserService).notifyAllUsers(anyString());
    }
}