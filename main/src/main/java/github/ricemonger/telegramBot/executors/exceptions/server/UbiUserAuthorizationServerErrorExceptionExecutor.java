package github.ricemonger.telegramBot.executors.exceptions.server;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiUserAuthorizationServerErrorExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Ubisoft server error during authorization. Please try again later");
    }
}
