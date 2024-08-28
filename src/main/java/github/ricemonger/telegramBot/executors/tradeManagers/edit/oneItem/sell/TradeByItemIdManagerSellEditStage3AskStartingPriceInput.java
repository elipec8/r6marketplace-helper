package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerSellEditStage3AskStartingPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_STARTING_SELL_PRICE);

        askFromInlineKeyboardOrSkip("Please enter starting price to sell item or skip to make it equal to boundary sell price:", 1);
    }
}
