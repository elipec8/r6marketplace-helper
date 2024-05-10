package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.credentials.add.CredentialsAddCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveAllCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveAllConfirmedCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveCallback;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveOneCallback;
import github.ricemonger.telegramBot.executors.credentials.show.CredentialsShowCallback;
import github.ricemonger.telegramBot.executors.start.startYes.StartYesCallback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CallbackCommandListenerTests {

    @MockBean
    private ExecutorsService executorsService;

    @Autowired
    private CallbackCommandListener callbackCommandListener;

    @Test
    public void handleUpdateShouldExecuteStartYesOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.START_YES);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(StartYesCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsAddOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_ADD);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsAddCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsRemoveOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_REMOVE);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsRemoveCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsRemoveAllOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_REMOVE_ALL);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsRemoveAllCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsRemoveAllConfirmedOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_REMOVE_ALL_CONFIRMED);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsRemoveAllConfirmedCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsRemoveOneOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_REMOVE_ONE);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsRemoveOneCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsShowOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CREDENTIALS_SHOW);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsShowCallback.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCancelOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.CANCEL);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteSilentCancelOnItsCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData(Callbacks.SILENT_CANCEL);
        callbackCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(SilentCancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldThrowIllegalStateExceptionOnUnexpectedCallback() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setCallbackQueryData("unexpected");
        assertThrows(IllegalStateException.class, () -> callbackCommandListener.handleUpdate(updateInfo));
    }
}