package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveAllCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute() {

        CredentialsRemoveAllCallback credentialsRemoveAllCallback = new CredentialsRemoveAllCallback();

        credentialsRemoveAllCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}