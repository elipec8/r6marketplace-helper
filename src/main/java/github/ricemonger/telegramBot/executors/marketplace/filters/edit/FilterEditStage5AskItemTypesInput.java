package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputState;

public class FilterEditStage5AskItemTypesInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TYPES);
        String types = botInnerService.getItemTypesString();
        askFromInlineKeyboardOrSkip("Please enter item types, dived by \",\" or \"|\". Types list:\n" + types, 1);
    }
}
