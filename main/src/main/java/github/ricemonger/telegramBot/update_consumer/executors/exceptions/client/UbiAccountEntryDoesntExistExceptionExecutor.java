package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("You don't have linked Ubisoft account");
    }
}
