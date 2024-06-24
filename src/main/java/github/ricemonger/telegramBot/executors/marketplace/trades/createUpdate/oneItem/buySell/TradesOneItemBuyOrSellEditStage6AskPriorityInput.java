package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buySell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyOrSellEditStage6AskPriorityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_PRIORITY);

        sendText("Please enter numeral priority of the trade, where the higher the number, the higher the priority:");
    }
}
