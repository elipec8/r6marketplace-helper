package github.ricemonger.telegramBot.client.executors.start.startYes;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StartYesCallbackTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShouldCheckIfUserIsRegisteredAndRegisterIfNot() {
        StartYesCallback startYesCallback = new StartYesCallback();
        when(botService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        startYesCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botService).registerUser(MockUpdateInfos.UPDATE_INFO.getChatId());
    }

    @Test
    public void initAndExecuteShouldSendTextIfUserIsRegistered() {
        StartYesCallback startYesCallback = new StartYesCallback();
        when(botService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        startYesCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botService, never()).registerUser(any());
        verify(botService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}