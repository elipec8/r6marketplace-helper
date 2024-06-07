package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;

public class FilterCreateStage13AskMinPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_MIN_PRICE);
        askFromInlineKeyboardOrSkip("Please enter min price:",1);
    }
}
