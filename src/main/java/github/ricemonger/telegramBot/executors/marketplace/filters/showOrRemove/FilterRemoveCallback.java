package github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterRemoveCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId());
        sendText("Filter successfully removed");
    }
}
