package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TelegramUserAlreadyExistsExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("You are already registered");
    }
}
