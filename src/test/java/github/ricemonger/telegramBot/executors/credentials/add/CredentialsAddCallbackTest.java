package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsAddCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShould() {
        CredentialsAddCallback credentialsAddCallback = new CredentialsAddCallback();

        credentialsAddCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.CREDENTIALS_FULL_OR_EMAIL);
        verify(botInnerService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.CREDENTIALS_ADD);

        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}