package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByItemIdManagerBuyEditStage3AskPriorityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);

        sendText("Please enter numeral priority of the trade, where the higher the number, the higher the priority. Default is 1:");
    }
}
