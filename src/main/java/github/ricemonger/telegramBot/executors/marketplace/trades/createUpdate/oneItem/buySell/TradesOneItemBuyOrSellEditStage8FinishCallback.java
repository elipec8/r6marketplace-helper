package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buySell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemBuyOrSellEditStage8FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.savePlannedOneItemTradeByUserInput(updateInfo.getChatId(), PlannedTradeType.BUY_AND_SELL);

        sendText("Trade saved successfully.");
    }
}
