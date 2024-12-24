package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerEditStage4AskMinBuySellProfitInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT);

        askFromInlineKeyboardOrSkip("""
                Please enter minimum median price difference. Default is 50:
                """, 1);
    }
}
