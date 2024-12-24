package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerEditStage5AskMinProfitPercentInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_MIN_MEDIAN_PRICE_DIFFERENCE_PERCENT);

        askFromInlineKeyboardOrSkip("""
                Please enter minimum median price difference percent. Default is 10:
                """, 1);
    }
}
