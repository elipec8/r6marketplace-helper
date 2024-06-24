package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyEditStage3AskBoundaryPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE);

        sendText("Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
