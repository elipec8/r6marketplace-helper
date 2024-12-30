package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UnexpectedDirectCommandExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Unexpected direct command entered");
    }
}
