package github.ricemonger.telegramBot.executors.exceptions.client;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiUserAuthorizationClientErrorExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Ubisoft authorization client error. Probably, wrong credentials entered");
    }
}
