package github.ricemonger.telegramBot.update_consumer.executors.exceptions.client;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiUserAuthorizationClientErrorExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Ubisoft authorization client error. Probably, wrong credentials entered");
    }
}
