package github.ricemonger.telegramBot.executors.marketplace.filters;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FiltersCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton createButton = new CallbackButton("Create/Update", Callbacks.FILTER_EDIT);
        CallbackButton showAllButton = new CallbackButton("Show/Remove", Callbacks.FILTERS_SHOW);
        askFromInlineKeyboard("Please choose operation?",2, createButton, showAllButton);
    }
}
