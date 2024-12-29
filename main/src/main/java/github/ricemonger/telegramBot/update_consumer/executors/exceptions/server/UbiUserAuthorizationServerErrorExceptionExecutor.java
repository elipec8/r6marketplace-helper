package github.ricemonger.telegramBot.update_consumer.executors.exceptions.server;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiUserAuthorizationServerErrorExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Ubisoft server error during authorization. Please try again later");
    }
}
