package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemBuyEditStage6FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.savePlannedOneItemTradeByUserInput(updateInfo.getChatId(), PlannedTradeType.BUY);

        sendText("Trade saved successfully.");
    }
}
