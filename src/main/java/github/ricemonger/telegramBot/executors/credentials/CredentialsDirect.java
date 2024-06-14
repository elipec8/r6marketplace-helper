package github.ricemonger.telegramBot.executors.credentials;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> {
            askFromInlineKeyboard(
                    "Please choose the action with credentials:",
                    1,
                    new CallbackButton("Add", Callbacks.CREDENTIALS_ADD),
                    new CallbackButton("Remove", Callbacks.CREDENTIALS_REMOVE),
                    new CallbackButton("Show", Callbacks.CREDENTIALS_SHOW)
            );
        };
        executeCommandOrAskToRegister(command);
    }
}
