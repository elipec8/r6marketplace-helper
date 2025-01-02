package github.ricemonger.telegramBot.update_consumer.executors.exceptions.server;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TelegramApiRuntimeExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Telegram error occurred. Please try again later");
    }
}
