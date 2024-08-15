package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.sell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemSellEditStage6FinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagerTradeType.SELL);

        sendText("Trade saved successfully.");
    }
}
