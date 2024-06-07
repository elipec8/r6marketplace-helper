package github.ricemonger.telegramBot.executors.marketplace.filters.create;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterCreateStage17FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filter = botInnerService.getFilterStringFromInput(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard("The filter is:\n" + filter +"\nDo you want to save the filter?",
                Callbacks.FILTER_CREATE_FINISH,
                Callbacks.CANCEL);
    }
}
