package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage17FinishRequestInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filter = botInnerService.generateItemFilterByUserInput(updateInfo.getChatId()).toString();

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filter + "\nDo you want to save the filter?",
                Callbacks.ITEM_FILTER_EDIT_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
