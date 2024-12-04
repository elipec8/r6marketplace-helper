package github.ricemonger.telegramBot;

import github.ricemonger.marketplace.services.TelegramUserService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramBotServiceTest {
    @Autowired
    private TelegramBotService telegramBotService;
    @MockBean
    private TelegramBotClientService telegramBotClientService;
    @MockBean
    private TelegramUserService telegramUserService;

    @Test
    public void sendNotificationToUser_should_handle_to_service() {
        telegramBotService.sendNotificationToUser("1", "message");

        verify(telegramBotClientService).sendText("1", "message");
    }

    @Test
    void notifyAllUsersAboutItemAmountIncrease_should_handle_to_services() {
        List<String> chatIds = new ArrayList<>();
        chatIds.add("1");
        chatIds.add("2");
        when(telegramUserService.getAllChatIdsForNotifiableUsers()).thenReturn(chatIds);

        telegramBotService.notifyAllUsersAboutItemAmountIncrease(1, 2);

        verify(telegramUserService).getAllChatIdsForNotifiableUsers();

        verify(telegramBotClientService).sendText(eq(chatIds.get(0)), anyString());
        verify(telegramBotClientService).sendText(eq(chatIds.get(1)), anyString());
    }
}