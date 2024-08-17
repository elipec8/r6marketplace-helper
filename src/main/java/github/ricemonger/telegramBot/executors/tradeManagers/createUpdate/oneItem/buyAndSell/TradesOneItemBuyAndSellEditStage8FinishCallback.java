package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemBuyAndSellEditStage8FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagerTradeType.BUY_AND_SELL);

        sendText("Trade saved successfully.");
    }
}
