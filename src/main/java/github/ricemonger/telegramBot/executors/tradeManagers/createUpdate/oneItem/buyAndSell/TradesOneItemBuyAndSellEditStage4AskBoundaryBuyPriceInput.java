package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyAndSellEditStage4AskBoundaryBuyPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE);

        sendText("Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
