package github.ricemonger.telegramBot.executors.exceptions.server;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ServerOrUnsupportedExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Server error occurred. Please try again later");
    }
}
