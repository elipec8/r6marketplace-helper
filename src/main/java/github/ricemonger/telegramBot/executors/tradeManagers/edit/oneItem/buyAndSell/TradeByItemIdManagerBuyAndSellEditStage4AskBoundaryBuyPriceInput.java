package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerBuyAndSellEditStage4AskBoundaryBuyPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE,"Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
