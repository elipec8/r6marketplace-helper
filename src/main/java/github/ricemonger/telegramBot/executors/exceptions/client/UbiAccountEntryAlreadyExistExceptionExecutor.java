package github.ricemonger.telegramBot.executors.exceptions.client;


import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryAlreadyExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("You already have linked Ubisoft account");
    }
}
