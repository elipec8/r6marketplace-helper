package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage14AskMaxPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_MAX_PRICE);
        askFromInlineKeyboardOrSkip("Please enter max price:", 1);
    }
}
