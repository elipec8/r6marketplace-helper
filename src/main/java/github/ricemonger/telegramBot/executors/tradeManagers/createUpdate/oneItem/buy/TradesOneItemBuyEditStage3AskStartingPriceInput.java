package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyEditStage3AskStartingPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE);

        askFromInlineKeyboardOrSkip("Please enter starting price to buy item or skip to make it equal to boundary buy price:", 1);
    }
}
