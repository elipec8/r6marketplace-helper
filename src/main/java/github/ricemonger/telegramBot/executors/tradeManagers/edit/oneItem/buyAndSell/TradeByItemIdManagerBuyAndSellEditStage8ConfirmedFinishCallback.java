package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagingType;

public class TradeByItemIdManagerBuyAndSellEditStage8ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagingType.BUY_AND_SELL);

        sendText("Trade saved successfully.");
    }
}
