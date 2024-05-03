package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.client.executors.ExecutorsService;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.cancel.Cancel;
import github.ricemonger.telegramBot.client.executors.cancel.SilentCancel;
import github.ricemonger.telegramBot.client.executors.credentials.add.CredentialsAddFullOrEmailInput;
import github.ricemonger.telegramBot.client.executors.credentials.add.CredentialsAddPasswordInput;
import github.ricemonger.telegramBot.client.executors.credentials.remove.CredentialsRemoveOneEmailInput;
import github.ricemonger.utils.exceptions.InvalidUserInputGroupException;
import github.ricemonger.utils.exceptions.InvalidUserInputStateAndGroupConjunctionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {
        if (cancelMessageTextOrCallbackQueryText(updateInfo)) {
            executorsService.execute(Cancel.class, updateInfo);
        } else if (silentCancelMessageTextOrCallbackQueryText(updateInfo)) {
            executorsService.execute(SilentCancel.class, updateInfo);
        } else {
            switch (updateInfo.getInputGroup()) {

                case CREDENTIALS_ADD -> credentialsAddInputGroup(updateInfo);

                case CREDENTIALS_REMOVE_ONE -> credentialsRemoveOneInputGroup(updateInfo);

                default -> throw new InvalidUserInputGroupException(updateInfo.getInputGroup().name());
            }
        }
    }

    private boolean cancelMessageTextOrCallbackQueryText(UpdateInfo updateInfo) {
        return (updateInfo.isHasMessage() && updateInfo.getMessageText().equals("/cancel"))
                ||
                updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.CANCEL);
    }

    private boolean silentCancelMessageTextOrCallbackQueryText(UpdateInfo updateInfo) {
        return (updateInfo.isHasMessage() && updateInfo.getMessageText().equals("/silentCancel"))
                ||
                updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.SILENT_CANCEL);
    }

    private void credentialsAddInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case InputState.CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsAddFullOrEmailInput.class, updateInfo);

            case InputState.CREDENTIALS_PASSWORD -> executorsService.execute(CredentialsAddPasswordInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void credentialsRemoveOneInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case InputState.CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsRemoveOneEmailInput.class, updateInfo);

            default ->
                    throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }
}
