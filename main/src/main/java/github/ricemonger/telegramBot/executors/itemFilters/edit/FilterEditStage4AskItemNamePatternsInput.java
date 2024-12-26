package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class FilterEditStage4AskItemNamePatternsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_NAME_PATTERNS);
        askFromInlineKeyboardOrSkip("Please enter item name patterns, dived by \",\" or \"|\":", 1);
    }
}
