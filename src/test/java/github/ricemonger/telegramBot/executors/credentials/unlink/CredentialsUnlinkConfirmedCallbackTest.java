package github.ricemonger.telegramBot.executors.credentials.unlink;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class CredentialsUnlinkConfirmedCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_remove_account_entry() {
        CredentialsUnlinkConfirmedCallback credentialsUnlinkConfirmedCallback = new CredentialsUnlinkConfirmedCallback();

        credentialsUnlinkConfirmedCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeUserUbiAccountEntry(MockUpdateInfos.UPDATE_INFO.getChatId());
    }

}