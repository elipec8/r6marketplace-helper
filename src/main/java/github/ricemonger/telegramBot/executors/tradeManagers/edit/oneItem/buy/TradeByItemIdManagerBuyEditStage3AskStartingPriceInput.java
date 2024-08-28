package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerBuyEditStage3AskStartingPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_ITEM_ID_MANAGER_STARTING_BUY_PRICE);

        askFromInlineKeyboardOrSkip("Please enter starting price to buy item or skip to make it equal to boundary buy price:", 1);
    }
}
