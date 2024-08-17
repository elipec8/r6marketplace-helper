package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersByItemIdRemoveStage3FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserTradeByItemIdManagerByUserInput(updateInfo.getChatId());
        sendText("Trade manager successfully removed");
    }
}
