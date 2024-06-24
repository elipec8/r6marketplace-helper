package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buySell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyOrSellEditStage2AskBoundarySellPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE);

        sendText("Please enter boundary price to sell item(If value is invalid, (current min sell price - 1) will be used):");
    }
}
