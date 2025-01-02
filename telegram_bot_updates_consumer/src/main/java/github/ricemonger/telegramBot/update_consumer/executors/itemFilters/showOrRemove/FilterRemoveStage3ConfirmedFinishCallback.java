package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class FilterRemoveStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId());
        sendText("Item Filter successfully removed");
    }
}
