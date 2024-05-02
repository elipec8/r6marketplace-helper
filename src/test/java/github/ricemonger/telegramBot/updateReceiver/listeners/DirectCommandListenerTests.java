package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.executors.ExecutorsService;
import github.ricemonger.telegramBot.client.executors.cancel.Cancel;
import github.ricemonger.telegramBot.client.executors.credentials.CredentialsDirect;
import github.ricemonger.telegramBot.client.executors.help.HelpDirect;
import github.ricemonger.telegramBot.client.executors.start.StartDirect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class DirectCommandListenerTests {

    @MockBean
    private ExecutorsService executorsService;

    @Autowired
    private DirectCommandListener directCommandListener;

    @Test
    public void handleUpdateShouldExecuteStartOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText("/start");

        directCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(StartDirect.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteHelpOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText("/help");

        directCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(HelpDirect.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText("/credentials");

        directCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsDirect.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCancelOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText("/cancel");

        directCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldThrowExceptionOnUnexpectedCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText("/unexpected");

        try {
            directCommandListener.handleUpdate(updateInfo);
        } catch (IllegalStateException e) {
            return;
        }

        throw new IllegalStateException("Expected IllegalStateException");
    }
}