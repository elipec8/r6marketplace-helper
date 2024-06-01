package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.utils.exceptions.InvalidUserInputStateAndGroupConjunctionException;
import github.ricemonger.utils.exceptions.ListeningMethodCouldNotBeChosenException;
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
    public void distributeShouldHandleToInputCommandListenerWhenUpdateHasNotBaseInputGroup() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(exceptionCommandListener, never()).handleException(any(), any());
    }

    @Test
    public void distributeShouldHandleToDirectCommandListenerWhenUpdateHasMessage() {
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
    public void distributeShouldHandleToCallbackCommandListenerWhenUpdateHasCallbackQuery() {
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
    public void distributeShouldHandleToExceptionCommandListenerIfListeningMethodCouldNotBeChosen() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.BASE);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(exceptionCommandListener).handleException(any(ListeningMethodCouldNotBeChosenException.class), any());

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
        verify(inputCommandListener, never()).handleUpdate(any());
    }

    @Test
    public void distributeShouldHandleToExceptionCommandListenerIfExceptionIsThrown() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setInputGroup(InputGroup.CREDENTIALS_ADD);
        doThrow(InvalidUserInputStateAndGroupConjunctionException.class).when(inputCommandListener).handleUpdate(updateInfo);

        updatesToListenersDistributor.distribute(updateInfo);

        verify(exceptionCommandListener).handleException(any(InvalidUserInputStateAndGroupConjunctionException.class), same(updateInfo));

        verify(inputCommandListener).handleUpdate(updateInfo);

        verify(directCommandListener, never()).handleUpdate(any());
        verify(callbackCommandListener, never()).handleUpdate(any());
    }
}