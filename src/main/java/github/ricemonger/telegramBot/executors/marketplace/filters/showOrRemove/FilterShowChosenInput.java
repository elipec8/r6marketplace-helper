package github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterShowChosenInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filterString = botInnerService.getFilterStringFromDatabaseByUserCallback(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filterString +"\nDo you want to remove the filter?",
                Callbacks.FILTER_REMOVE_FINISH,
                Callbacks.CANCEL);
    }
}
