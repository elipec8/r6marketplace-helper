package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterCreateFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveFilterFromInput(updateInfo.getChatId());
        botInnerService.clearUserInputs(updateInfo.getChatId());
        sendText("Filter created successfully");
    }
}
