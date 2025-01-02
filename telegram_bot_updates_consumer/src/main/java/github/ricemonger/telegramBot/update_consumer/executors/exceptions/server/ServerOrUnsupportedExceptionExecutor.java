package github.ricemonger.telegramBot.update_consumer.executors.exceptions.server;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ServerOrUnsupportedExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Server error occurred. Please try again later");
    }
}
