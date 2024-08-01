package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveOneEmailInputTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShould() {
        CredentialsRemoveOneEmailInput credentialsRemoveOneEmailInput = new CredentialsRemoveOneEmailInput();

        credentialsRemoveOneEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeCredentialsByUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botInnerService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);
    }

}