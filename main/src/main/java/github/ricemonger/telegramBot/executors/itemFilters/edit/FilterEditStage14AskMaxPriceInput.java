package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class FilterEditStage14AskMaxPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_MAX_PRICE);
        askFromInlineKeyboardOrSkip("Please enter max price:", 1);
    }
}
