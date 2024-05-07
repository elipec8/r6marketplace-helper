package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.executors.credentials.add.CredentialsAddFullOrEmailInput;
import github.ricemonger.telegramBot.executors.credentials.add.CredentialsAddPasswordInput;
import github.ricemonger.telegramBot.executors.credentials.remove.CredentialsRemoveOneEmailInput;
import github.ricemonger.utils.exceptions.InvalidUserInputGroupException;
import github.ricemonger.utils.exceptions.InvalidUserInputStateAndGroupConjunctionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class InputCommandListenerTests {

    @MockBean
    private ExecutorsService executorsService;

    @Autowired
    private InputCommandListener inputCommandListener;

    @Test
    public void handleUpdateShouldExecuteCancelOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("/cancel");

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCancelOnItsCallbackQuery() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasCallBackQuery(true);
        updateInfo.setCallbackQueryData(Callbacks.CANCEL);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(Cancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteSilentCancelOnItsCommand() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasMessage(true);
        updateInfo.setMessageText("/silentCancel");

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(SilentCancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteSilentCancelOnItsCallbackQuery() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setHasCallBackQuery(true);
        updateInfo.setCallbackQueryData(Callbacks.SILENT_CANCEL);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(SilentCancel.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsAddFullOrEmailInputOnItsInputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsAddFullOrEmailInput.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsAddPasswordInputOnItsInputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setInputState(InputState.CREDENTIALS_PASSWORD);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsAddPasswordInput.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldThrowExceptionOnInvalidInputStateAndGroupConjunctionForCredentialsAddGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        updateInfo.setInputState(InputState.BASE);

        assertThrows(InvalidUserInputStateAndGroupConjunctionException.class, () -> inputCommandListener.handleUpdate(updateInfo));
    }

    @Test
    public void handleUpdateShouldExecuteCredentialsRemoveOneInputEmailOnItsInputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_REMOVE_ONE);
        updateInfo.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);

        inputCommandListener.handleUpdate(updateInfo);

        verify(executorsService).execute(CredentialsRemoveOneEmailInput.class, updateInfo);
    }

    @Test
    public void handleUpdateShouldThrowExceptionOnInvalidInputStateAndGroupConjunctionForCredentialsRemoveOneGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_REMOVE_ONE);
        updateInfo.setInputState(InputState.CREDENTIALS_PASSWORD);

        assertThrows(InvalidUserInputStateAndGroupConjunctionException.class, () -> inputCommandListener.handleUpdate(updateInfo));
    }

    @Test
    public void handleUpdateShouldThrowExceptionOnInvalidInputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.BASE);

        assertThrows(InvalidUserInputGroupException.class, () -> inputCommandListener.handleUpdate(updateInfo));
    }
}