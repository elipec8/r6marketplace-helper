package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputState;

public class FilterEditStage13AskMinPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_MIN_PRICE);
        askFromInlineKeyboardOrSkip("Please enter min price:",1);
    }
}
