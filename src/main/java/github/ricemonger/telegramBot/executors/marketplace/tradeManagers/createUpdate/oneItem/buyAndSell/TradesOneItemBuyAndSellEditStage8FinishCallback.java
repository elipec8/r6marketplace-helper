package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemBuyAndSellEditStage8FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.savePlannedOneItemTradeByUserInput(updateInfo.getChatId(), TradeManagerTradeType.BUY_AND_SELL);

        sendText("Trade saved successfully.");
    }
}
