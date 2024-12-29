package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserTradeByItemIdManagerByUserInput(updateInfo.getChatId());
        sendText("Trade manager successfully removed");
    }
}
