package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerEditStage6AskPriorityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_PRIORITY);

        askFromInlineKeyboardOrSkip("Please enter numeral priority of the trade, where the higher the number, the higher the priority. Default is 1:", 1);
    }
}
