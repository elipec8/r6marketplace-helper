package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage4AskItemNamePatternsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_NAME_PATTERNS);
        askFromInlineKeyboardOrSkip("Please enter item name patterns, dived by \",\" or \"|\":", 1);
    }
}
