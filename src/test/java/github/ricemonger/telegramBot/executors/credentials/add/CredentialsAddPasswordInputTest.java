package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsAddPasswordInputTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShould() {

        CredentialsAddPasswordInput credentialsAddPasswordInput = new CredentialsAddPasswordInput();
        credentialsAddPasswordInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT, botInnerService);

        verify(botInnerService).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId());

        verify(botInnerService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT);
        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId(), InputState.BASE);
        verify(botInnerService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId(), InputGroup.BASE);
    }

}