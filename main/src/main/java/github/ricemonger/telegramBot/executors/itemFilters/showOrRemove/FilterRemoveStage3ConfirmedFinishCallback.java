package github.ricemonger.telegramBot.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterRemoveStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId());
        sendText("Item Filter successfully removed");
    }
}
