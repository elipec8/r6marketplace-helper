package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CredentialsShowCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldSendListOfCredentials() {
        CredentialsShowCallback credentialsShowCallback = new CredentialsShowCallback();
        when(botInnerService.getCredentialsEmailsList(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(List.of("email1", "email2"));

        credentialsShowCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getCredentialsEmailsList(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), contains("email1"));
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), contains("email2"));
    }
}