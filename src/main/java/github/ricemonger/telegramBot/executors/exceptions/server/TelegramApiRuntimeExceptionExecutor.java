package github.ricemonger.telegramBot.executors.exceptions.server;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TelegramApiRuntimeExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Telegram error occurred. Please try again later");
    }
}
