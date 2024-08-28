package github.ricemonger.telegramBot.executors.exceptions.client;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

import static github.ricemonger.telegramBot.client.PublicBotCommands.START_COMMAND;

public class TelegramUserDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("You are not registered. Please use "+ START_COMMAND +" command to register");
    }
}
