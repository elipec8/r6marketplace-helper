package github.ricemonger.telegramBot.client.executors.credentials;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askFromInlineKeyboardOrCancel(
                "Please choose the action with credentials:",
                1,
                new CallbackButton("Add", Callbacks.CREDENTIALS_ADD),
                new CallbackButton("Remove", Callbacks.CREDENTIALS_REMOVE),
                new CallbackButton("Show", Callbacks.CREDENTIALS_SHOW)
                );
    }
}
