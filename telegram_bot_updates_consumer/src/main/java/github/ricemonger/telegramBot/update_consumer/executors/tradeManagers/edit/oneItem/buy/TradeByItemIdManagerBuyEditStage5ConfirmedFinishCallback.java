package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeOperationType;

public class TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeOperationType.BUY);

        sendText("Trade saved successfully.");
    }
}
