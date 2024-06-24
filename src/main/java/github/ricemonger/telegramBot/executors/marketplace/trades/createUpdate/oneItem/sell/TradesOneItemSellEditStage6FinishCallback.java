package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.sell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemSellEditStage6FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.savePlannedOneItemTradeByUserInput(updateInfo.getChatId(), PlannedTradeType.SELL);

        sendText("Trade saved successfully.");
    }
}
