package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.marketplace.services.DTOs.NotificationsSettings;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class NotificationsDirectTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_get_user_notif_settings_and_ask_from_inline_keyboard() {
        NotificationsSettings settings = new NotificationsSettings();
        when(botInnerService.getUserNotificationsSettings(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(settings);

        NotificationsDirect NotificationsDirect = new NotificationsDirect();
        NotificationsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserNotificationsSettings(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}