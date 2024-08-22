package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage18FinishConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserItemFilterByUserInput(updateInfo.getChatId());
        sendText("Filter created successfully");
    }
}
