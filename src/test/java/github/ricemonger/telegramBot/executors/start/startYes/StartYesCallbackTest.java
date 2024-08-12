package github.ricemonger.telegramBot.executors.start.startYes;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class StartYesCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldCheckIfUserIsRegisteredAndRegisterIfNot() {
        StartYesCallback startYesCallback = new StartYesCallback();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        startYesCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).registerUser(MockUpdateInfos.UPDATE_INFO.getChatId());
    }

    @Test
    public void initAndExecuteShouldSendTextIfUserIsRegistered() {
        StartYesCallback startYesCallback = new StartYesCallback();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        startYesCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService, never()).registerUser(any());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}