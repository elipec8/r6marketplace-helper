package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemFilterDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Item filter with such name doesn't exist");
    }
}
