package github.ricemonger.telegramBot.client.executors.credentials.show;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CredentialsShowCallbackTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShouldSendListOfCredentials() {
        CredentialsShowCallback credentialsShowCallback = new CredentialsShowCallback();
        when(botService.getCredentialsEmailsList(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(List.of("email1", "email2"));

        credentialsShowCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).getCredentialsEmailsList(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botService).sendText(eq(MockUpdateInfos.UPDATE_INFO),contains("email1"));
        verify(botService).sendText(eq(MockUpdateInfos.UPDATE_INFO),contains("email2"));
    }
}