package github.ricemonger.telegramBot.executors.marketplace.items.show.showByRequest;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowByRequestFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        String filter = botInnerService.getFilterStringByUserInput(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard("The filtering options are:\n" + filter +"\nDo you want to begin search?",
                Callbacks.ITEMS_SHOW_BY_REQUEST_FINISH,
                Callbacks.CANCEL);
    }
}
