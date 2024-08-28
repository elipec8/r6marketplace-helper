package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerEditStage6AskPriorityInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_PRIORITY);

        askFromInlineKeyboardOrSkip("""
                Please enter priority of trade manager.
                It's used only to determine this manager's trades priority relatively to other managers' trades.
                Can be assigned any positive value. Will be assigned 1 if skipped :
                """, 1);
    }
}
