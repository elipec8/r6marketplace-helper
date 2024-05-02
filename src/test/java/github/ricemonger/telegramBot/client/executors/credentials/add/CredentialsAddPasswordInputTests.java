package github.ricemonger.telegramBot.client.executors.credentials.add;

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
class CredentialsAddPasswordInputTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShould(){

        CredentialsAddPasswordInput credentialsAddPasswordInput = new CredentialsAddPasswordInput();
        credentialsAddPasswordInput.initAndExecute(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT,botService);

        verify(botService).addCredentialsFromUserInputs(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId());

        verify(botService).saveUserInputOrThrow(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT);
        verify(botService).setUserNextInputState(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId(), InputState.BASE);
        verify(botService).setUserNextInputGroup(MockUpdateInfos.UPDATE_INFO_PASSWORD_INPUT.getChatId(), InputGroup.BASE);
    }

}