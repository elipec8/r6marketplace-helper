package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsAddCallbackTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShould(){
        CredentialsAddCallback credentialsAddCallback = new CredentialsAddCallback();

        credentialsAddCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.CREDENTIALS_FULL_OR_EMAIL);
        verify(botService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.CREDENTIALS_ADD);

        verify(botService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}