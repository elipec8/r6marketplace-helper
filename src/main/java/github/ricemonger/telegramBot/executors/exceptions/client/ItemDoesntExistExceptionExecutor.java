package github.ricemonger.telegramBot.executors.exceptions.client;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Item with such ID doesn't exist");
    }
}
