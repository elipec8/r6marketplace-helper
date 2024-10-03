package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagingType;

public class TradeByItemIdManagerSellEditStage6ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagingType.SELL);

        sendText("Trade saved successfully.");
    }
}
