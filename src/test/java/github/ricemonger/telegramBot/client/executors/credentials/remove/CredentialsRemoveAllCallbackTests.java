package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveAllCallbackTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecute(){

        CredentialsRemoveAllCallback credentialsRemoveAllCallback = new CredentialsRemoveAllCallback();

        credentialsRemoveAllCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO,botService);

        verify(botService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}