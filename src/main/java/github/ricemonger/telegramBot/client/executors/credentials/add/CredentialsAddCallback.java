package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;

public class CredentialsAddCallback extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        String text = "Please provide your Ubisoft Account's email and password separated in two messages OR in the following format: email:password";

        processFirstInput(updateInfo, InputState.CREDENTIALS_FULL_OR_EMAIL, InputGroup.CREDENTIALS, text);
    }
}
