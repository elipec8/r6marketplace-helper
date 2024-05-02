package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.BotService;
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
    private BotService botService;

    @Test
    public void initAndExecuteShouldAddCredentialsIfFullInput() {
        CredentialsAddFullOrEmailInput credentialsAddFullOrEmailInput = new CredentialsAddFullOrEmailInput();

        credentialsAddFullOrEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_FULL_INPUT, botService);

        verify(botService).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId());

        verify(botService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_FULL_INPUT);
        verify(botService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId(), InputGroup.BASE);
        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId(), InputState.BASE);
    }

    @Test
    public void initAndExecuteShouldRequestPasswordIfEmailInput() {
        CredentialsAddFullOrEmailInput credentialsAddFullOrEmailInput = new CredentialsAddFullOrEmailInput();

        credentialsAddFullOrEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT, botService);

        verify(botService, never()).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_FULL_INPUT.getChatId());

        verify(botService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT);
        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_EMAIL_INPUT.getChatId(), InputState.CREDENTIALS_PASSWORD);
    }

}