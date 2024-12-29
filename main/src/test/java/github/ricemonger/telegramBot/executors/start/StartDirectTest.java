package github.ricemonger.telegramBot.executors.start;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.start.StartDirect;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StartDirectTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void init_and_execute_should_ask_to_register_if_not_registered() {
        StartDirect startDirect = new StartDirect();

        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        startDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }

    @Test
    public void init_and_execute_should_NOT_ask_to_register_and_send_text_if_registered() {
        StartDirect startDirect = new StartDirect();

        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        startDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService, times(0)).askFromInlineKeyboard(any(), any(), anyInt(), any());
    }
}
