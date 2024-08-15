package github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterShowChosenInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filterString = botInnerService.getUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId()).toString();

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filterString + "\nDo you want to remove the filter?",
                Callbacks.FILTER_REMOVE_FINISH,
                Callbacks.CANCEL);
    }
}
