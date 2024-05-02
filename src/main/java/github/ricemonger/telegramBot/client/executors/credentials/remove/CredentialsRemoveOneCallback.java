package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;

public class CredentialsRemoveOneCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.CREDENTIALS_FULL_OR_EMAIL,
                InputGroup.CREDENTIALS_REMOVE_ONE,
                "Please provide the email of the credentials you want to remove.");
    }
}
