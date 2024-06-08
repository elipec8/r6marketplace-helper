package github.ricemonger.telegramBot.executors.marketplace.items.show.showByRequest;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowByRequestFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Searching items...");
        botInnerService.sendItemsAsMessageByUserInputFilter(updateInfo.getChatId());
    }
}
