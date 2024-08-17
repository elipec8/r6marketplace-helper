package github.ricemonger.telegramBot.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterRemoveConfirmedCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId());
        sendText("Item Filter successfully removed");
    }
}
