package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askFromInlineKeyboardOrCancel(
                "Do you want to remove One or All of your credentials?",
                1,
                new CallbackButton("One", Callbacks.CREDENTIALS_REMOVE_ONE),
                new CallbackButton("All", Callbacks.CREDENTIALS_REMOVE_ALL));
    }
}
