package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;


import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryAlreadyExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("You already have linked Ubisoft account");
    }
}
