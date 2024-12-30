package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class FilterShowChosenStage2RemoveRequestInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filterString = botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(updateInfo).toHandsomeString();

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filterString + "\nDo you want to remove the filter?",
                Callbacks.ITEM_FILTER_REMOVE_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
