package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage18FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserItemFilterByUserInput(updateInfo.getChatId());
        sendText("Filter created successfully");
    }
}
