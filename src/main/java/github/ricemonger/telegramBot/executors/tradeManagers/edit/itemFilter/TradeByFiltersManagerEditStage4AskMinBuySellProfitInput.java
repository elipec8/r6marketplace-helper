package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerEditStage4AskMinBuySellProfitInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_BUY_SELL_PROFIT);

        askFromInlineKeyboardOrSkip("Please enter minimum profit to get from following one another Buy and Sell operations." +
                                    "It is calculated as (S*0.9-B), where S - expected sell price, B - expected buy price," +
                                    "0.9 is used cause of 10% marketplace fee for sell operations." +
                                    "It will be used to filter possible trades with too small expected profit from resale." +
                                    "Can be assigned negative value to allow trades with loss." +
                                    "Will be assigned 50 if skipped. :", 1);
    }
}
