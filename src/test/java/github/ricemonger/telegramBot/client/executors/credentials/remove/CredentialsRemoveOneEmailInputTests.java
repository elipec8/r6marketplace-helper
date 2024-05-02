package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveOneEmailInputTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShould(){
        CredentialsRemoveOneEmailInput credentialsRemoveOneEmailInput = new CredentialsRemoveOneEmailInput();

        credentialsRemoveOneEmailInput.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).removeCredentialsByUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO);
        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE);
        verify(botService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.BASE);
    }

}