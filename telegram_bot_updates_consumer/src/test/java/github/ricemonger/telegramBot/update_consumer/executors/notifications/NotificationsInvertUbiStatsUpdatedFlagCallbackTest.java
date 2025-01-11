package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationsInvertUbiStatsUpdatedFlagCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_invert_flag_and_send_text() {
        NotificationsInvertUbiStatsUpdatedFlagCallback command = new NotificationsInvertUbiStatsUpdatedFlagCallback();
        command.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).invertUserUbiStatsUpdatedNotificationsFlag(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}