package github.ricemonger.telegramBot.executors.cancel;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class Cancel extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        cancel();
    }
}
