package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class FilterEditStage13AskMaxPriceInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_MAX_PRICE);
        askFromInlineKeyboardOrSkip("Please enter max price:", 1);
    }
}
