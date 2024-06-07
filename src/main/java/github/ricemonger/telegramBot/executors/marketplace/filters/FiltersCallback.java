package github.ricemonger.telegramBot.executors.marketplace.filters;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FiltersCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton createButton = new CallbackButton("Create", Callbacks.FILTER_CREATE);
        CallbackButton removeButton = new CallbackButton("Remove", Callbacks.FILTER_REMOVE);
        CallbackButton showAllButton = new CallbackButton("Show all", Callbacks.FILTERS_SHOW_ALL);
        askFromInlineKeyboard("Please choose operation?",1, createButton, removeButton, showAllButton);
    }
}
