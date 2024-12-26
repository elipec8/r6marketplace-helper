package github.ricemonger.telegramBot.executors.cancel;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class SilentCancel extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        silentCancel();
    }
}
