package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;

public class FilterEditStage16AskMaxLastSoldPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_MAX_LAST_SOLD_PRICE);
        askFromInlineKeyboardOrSkip("Please enter maximum last sold price:",1);
    }
}
