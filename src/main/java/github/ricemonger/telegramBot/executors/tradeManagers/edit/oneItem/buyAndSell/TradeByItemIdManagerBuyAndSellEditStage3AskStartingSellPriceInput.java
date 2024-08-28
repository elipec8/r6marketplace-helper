package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerBuyAndSellEditStage3AskStartingSellPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_STARTING_SELL_PRICE);

        askFromInlineKeyboardOrSkip("Please enter starting price to sell item or skip to make it equal to boundary sell price:", 1);
    }
}
