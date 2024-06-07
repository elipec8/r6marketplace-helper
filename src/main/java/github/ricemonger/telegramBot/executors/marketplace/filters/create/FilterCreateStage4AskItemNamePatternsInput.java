package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;

public class FilterCreateStage4AskItemNamePatternsInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_NAME_PATTERNS);
        askFromInlineKeyboardOrSkip("Please enter item name patterns, dived by \",\" or \"|\":", 1);
    }
}
