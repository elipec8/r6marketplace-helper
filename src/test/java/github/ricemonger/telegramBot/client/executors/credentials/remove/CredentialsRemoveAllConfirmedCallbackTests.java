package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsRemoveAllConfirmedCallbackTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShould(){

        CredentialsRemoveAllConfirmedCallback credentialsRemoveAllConfirmedCallback = new CredentialsRemoveAllConfirmedCallback();

        credentialsRemoveAllConfirmedCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeUserAllCredentials(MockUpdateInfos.UPDATE_INFO.getChatId());
    }

}