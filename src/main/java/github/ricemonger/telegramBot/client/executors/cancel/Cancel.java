package github.ricemonger.telegramBot.client.executors.cancel;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class Cancel extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        cancel();
    }
}
