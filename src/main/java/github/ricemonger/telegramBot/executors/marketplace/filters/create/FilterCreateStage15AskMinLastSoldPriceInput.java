package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;

public class FilterCreateStage15AskMinLastSoldPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_MIN_LAST_SOLD_PRICE);
        askFromInlineKeyboardOrSkip("Please enter minimum last sold price:",1);
    }
}
