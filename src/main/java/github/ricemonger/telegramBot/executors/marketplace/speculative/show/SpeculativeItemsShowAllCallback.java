package github.ricemonger.telegramBot.executors.marketplace.speculative.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class SpeculativeItemsShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.sendDefaultSpeculativeItemsAsMessages(updateInfo.getChatId());
    }
}
