package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.client.executors.ExecutorsService;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.telegramBot.client.executors.cancel.Cancel;
import github.ricemonger.telegramBot.client.executors.credentials.add.CredentialsAddInputFullOrEmail;
import github.ricemonger.telegramBot.client.executors.credentials.add.CredentialsAddInputPassword;
import github.ricemonger.telegramBot.client.executors.credentials.remove.CredentialsRemoveOneInputEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {
        if (cancelMessageTextOrCallbackQueryText(updateInfo)) {
            executorsService.execute(Cancel.class, updateInfo);
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
                updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.CANCEL)
                ||
                updateInfo.isHasCallBackQuery() && updateInfo.getCallbackQueryData().equals(Callbacks.SILENT_CANCEL);
    }

    private void credentialsAddInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case InputState.CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsAddInputFullOrEmail.class, updateInfo);

            case InputState.CREDENTIALS_PASSWORD -> executorsService.execute(CredentialsAddInputPassword.class, updateInfo);

            default -> throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }

    private void credentialsRemoveOneInputGroup(UpdateInfo updateInfo) {
        InputState inputState = updateInfo.getInputState();

        switch (inputState) {

            case InputState.CREDENTIALS_FULL_OR_EMAIL -> executorsService.execute(CredentialsRemoveOneInputEmail.class, updateInfo);

            default -> throw new InvalidUserInputStateAndGroupConjunctionException(updateInfo.getInputState().name() + " - state:group - " + updateInfo.getInputGroup().name());
        }
    }
}
