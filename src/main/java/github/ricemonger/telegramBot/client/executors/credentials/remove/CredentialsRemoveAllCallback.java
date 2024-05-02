package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard(
                "Are you sure you want to remove all your credentials?",
                Callbacks.CREDENTIALS_REMOVE_ALL_CONFIRMED,
                Callbacks.CANCEL);
    }
}
