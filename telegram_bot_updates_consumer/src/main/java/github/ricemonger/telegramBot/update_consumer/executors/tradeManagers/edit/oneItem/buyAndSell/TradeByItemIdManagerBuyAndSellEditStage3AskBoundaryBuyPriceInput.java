package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByItemIdManagerBuyAndSellEditStage3AskBoundaryBuyPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE, "Please enter boundary price to buy item(If value is invalid, next rounded buy price will be used):");
    }
}
