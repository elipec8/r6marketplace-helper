package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveAllConfirmedCallbackTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShould(){

        CredentialsRemoveAllConfirmedCallback credentialsRemoveAllConfirmedCallback = new CredentialsRemoveAllConfirmedCallback();

        credentialsRemoveAllConfirmedCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).removeUserAllCredentials(MockUpdateInfos.UPDATE_INFO.getChatId());
    }

}