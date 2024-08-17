package github.ricemonger.telegramBot.executors.credentials.unlink;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsUnlinkRequestCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard(
                "Are you sure you want to remove all your credentials?",
                Callbacks.CREDENTIALS_REMOVE_ALL_CONFIRMED,
                Callbacks.CANCEL);
    }
}
