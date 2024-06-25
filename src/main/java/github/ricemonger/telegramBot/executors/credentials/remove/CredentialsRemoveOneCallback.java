package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;

public class CredentialsRemoveOneCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.CREDENTIALS_FULL_OR_EMAIL,
                InputGroup.CREDENTIALS_REMOVE_ONE,
                "Please provide the email of the credentials you want to remove.");
    }
}
