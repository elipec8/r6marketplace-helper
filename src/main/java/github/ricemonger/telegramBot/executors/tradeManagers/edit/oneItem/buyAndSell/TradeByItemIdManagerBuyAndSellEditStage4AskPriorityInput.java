package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerBuyAndSellEditStage4AskPriorityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_PRIORITY);

        sendText("Please enter numeral priority of the trade, where the higher the number, the higher the priority. Default is 1:");
    }
}
