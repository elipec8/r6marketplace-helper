package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerEditStage5AskMinProfitPercentInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_MIN_MEDIAN_PRICE_DIFFERENCE_PERCENT);

        askFromInlineKeyboardOrSkip("""
                Please enter minimum median price difference percent. Default is 10:
                """, 1);
    }
}
