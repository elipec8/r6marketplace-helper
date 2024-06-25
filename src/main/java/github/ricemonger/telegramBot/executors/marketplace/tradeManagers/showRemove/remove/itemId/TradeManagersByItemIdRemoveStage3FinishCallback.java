package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersByItemIdRemoveStage3FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeTradeManagerByItemIdByUserInput(updateInfo.getChatId());
        sendText("Trade manager successfully removed");
    }
}
