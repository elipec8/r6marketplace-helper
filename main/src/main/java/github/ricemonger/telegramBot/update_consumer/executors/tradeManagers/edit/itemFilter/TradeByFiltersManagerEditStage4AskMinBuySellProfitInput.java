package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerEditStage4AskMinBuySellProfitInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_MIN_BUY_SELL_PROFIT);

        askFromInlineKeyboardOrSkip("""
                Please enter minimum median price difference. Default is 50:
                """, 1);
    }
}
