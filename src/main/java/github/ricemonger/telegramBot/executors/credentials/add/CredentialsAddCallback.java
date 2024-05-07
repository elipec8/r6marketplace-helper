package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class CredentialsAddCallback extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        String text = "Please provide your Ubisoft Account's email and password separated in two messages OR in the following format: email:password";

        processFirstInput(InputState.CREDENTIALS_FULL_OR_EMAIL, InputGroup.CREDENTIALS_ADD, text);
    }
}
