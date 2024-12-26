package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.exceptions.server.ListeningMethodCouldNotBeChosenException;
import github.ricemonger.utils.exceptions.server.UnexpectedUserInputStateAndGroupConjunctionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class UpdatesToListenersDistributorTest {

    @MockBean
    private CallbackCommandListener callbackCommandListener;

    @MockBean
    private DirectCommandListener directCommandListener;

    @MockBean
    private InputCommandListener inputCommandListener;

    @MockBean
    private ExceptionCommandListener exceptionCommandListener;

    @Autowired
    private UpdatesToListenersDistributor updatesToListenersDistributor;

    @Test
    public void distribute_should_handle_to_InputCommandListener_when_Update_has_not_base_InputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distribute_should_handle_to_InputCommandListener_when_Update_has_not_base_InputGroup_and_has_message() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        updateInfo.setHasMessage(true);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distribute_should_handle_to_InputCommandListener_when_update_has_not_base_inputGroup_and_has_callbackQuery() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        updateInfo.setHasCallBackQuery(true);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distribute_should_handle_to_DirectCommandListener_when_update_has_message_and_base_inputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.BASE);
        updateInfo.setHasMessage(true);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(directCommandListener).handleUpdate(updateInfo);

        verify(inputCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distribute_should_handle_to_CallbackCommandListener_when_update_has_callbackQuery_and_base_inputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.BASE);
        updateInfo.setHasCallBackQuery(true);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(callbackCommandListener).handleUpdate(updateInfo);

        verify(inputCommandListener, never()).handleUpdate(any());
        verify(directCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distribute_should_handle_to_ExceptionCommandListener_if_listening_method_could_not_be_chosen() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.BASE);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(exceptionCommandListener).handleException(any(ListeningMethodCouldNotBeChosenException.class), any());

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(inputCommandListener, never()).handleUpdate(any());
    }

    @Test
    public void distribute_should_handle_to_ExceptionCommandListener_if_exception_was_thrown() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);
        doThrow(UnexpectedUserInputStateAndGroupConjunctionException.class).when(inputCommandListener).handleUpdate(updateInfo);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(exceptionCommandListener).handleException(any(UnexpectedUserInputStateAndGroupConjunctionException.class), same(updateInfo));

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
    }
}