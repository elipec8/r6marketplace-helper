package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsAddFullOrEmailInputTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldAddCredentialsIfFullInput() {
        CredentialsAddFullOrEmailInput credentialsAddFullOrEmailInput = new CredentialsAddFullOrEmailInput();

        credentialsAddFullOrEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_FULL_INPUT, botInnerService);

        verify(botInnerService).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId());

        verify(botInnerService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_FULL_INPUT);
        verify(botInnerService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId(), InputGroup.BASE);
        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId(), InputState.BASE);
    }

    @Test
    public void initAndExecuteShouldRequestPasswordIfEmailInput() {
        CredentialsAddFullOrEmailInput credentialsAddFullOrEmailInput = new CredentialsAddFullOrEmailInput();

        credentialsAddFullOrEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT, botInnerService);

        verify(botInnerService, never()).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId());

        verify(botInnerService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT);
        verify(botInnerService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT.getChatId(), InputState.CREDENTIALS_PASSWORD);
    }

}