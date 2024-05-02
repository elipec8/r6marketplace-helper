package github.ricemonger.telegramBot.client.executors.cancel;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class SilentCancel extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        silentCancel();
    }
}
