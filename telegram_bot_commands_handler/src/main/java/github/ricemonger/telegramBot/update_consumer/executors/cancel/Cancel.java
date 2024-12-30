package github.ricemonger.telegramBot.update_consumer.executors.cancel;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class Cancel extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        cancel();
    }
}
