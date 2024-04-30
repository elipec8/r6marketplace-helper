package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdatesToListenersDistributor {

    private final CallbackCommandListener callbackCommandListener;

    private final DirectCommandListener directCommandListener;

    private final InputCommandListener inputCommandListener;

    private final ExceptionCommandListener exceptionCommandListener;

    public void distribute(UpdateInfo updateInfo) {
        try {
            if (updateHasNotBaseInputGroup(updateInfo)) {
                inputCommandListener.handleUpdate(updateInfo);
            }
            else if (updateHasMessage(updateInfo)) {
                directCommandListener.handleUpdate(updateInfo);
            }
            else if (updateHasCallbackQuery(updateInfo)) {
                callbackCommandListener.handleUpdate(updateInfo);
            } else {
                log.error("No listening method chosen for updateInfo-{}", updateInfo);
                throw new ListeningMethodCouldNotBeChosenException();
            }
        } catch (Exception e) {
            exceptionCommandListener.handleException(e, updateInfo);
        }
    }

    private boolean updateHasNotBaseInputGroup(UpdateInfo updateInfo) {
        return updateInfo.getInputGroup() != InputGroup.BASE;
    }

    private boolean updateHasMessage(UpdateInfo updateInfo) {
        return updateInfo.isHasMessage();
    }

    private boolean updateHasCallbackQuery(UpdateInfo updateInfo) {
        return updateInfo.isHasCallBackQuery();
    }
}
