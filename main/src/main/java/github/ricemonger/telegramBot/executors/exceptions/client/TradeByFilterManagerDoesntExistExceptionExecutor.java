package github.ricemonger.telegramBot.executors.exceptions.client;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFilterManagerDoesntExistExceptionExecutor extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Trade by filter Manager with such name doesn't exist");
    }
}
