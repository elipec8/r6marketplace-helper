package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class FilterEditStage14FinishRequestInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filter = botInnerService.generateItemFilterByUserInput(updateInfo.getChatId()).toHandsomeString();

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filter + "\nDo you want to save the filter?",
                Callbacks.ITEM_FILTER_EDIT_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
