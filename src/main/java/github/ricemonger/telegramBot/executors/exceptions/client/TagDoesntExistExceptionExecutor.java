package github.ricemonger.telegramBot.executors.exceptions.client;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TagDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Tag doesn't exist");
    }
}
