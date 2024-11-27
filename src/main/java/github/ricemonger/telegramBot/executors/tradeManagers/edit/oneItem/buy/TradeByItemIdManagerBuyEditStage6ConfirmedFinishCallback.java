package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeOperationType;

public class TradeByItemIdManagerBuyEditStage6ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeOperationType.BUY);

        sendText("Trade saved successfully.");
    }
}
