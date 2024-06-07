package github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterRemoveFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeFilterByUserCallback(updateInfo.getChatId());
        botInnerService.clearUserInputs(updateInfo.getChatId());
        sendText("Filter successfully removed");
    }
}
