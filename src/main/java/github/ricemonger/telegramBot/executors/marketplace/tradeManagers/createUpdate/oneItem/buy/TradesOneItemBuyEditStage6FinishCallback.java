package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemBuyEditStage6FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.savePlannedOneItemTradeByUserInput(updateInfo.getChatId(), TradeManagerTradeType.BUY);

        sendText("Trade saved successfully.");
    }
}
