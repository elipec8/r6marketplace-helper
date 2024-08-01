package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage17FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filter = botInnerService.getItemFilterByUserInput(updateInfo.getChatId()).toString();

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filter + "\nDo you want to save the filter?",
                Callbacks.FILTER_EDIT_FINISH,
                Callbacks.CANCEL);
    }
}
