package github.ricemonger.telegramBot.executors.itemFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemFiltersDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = ()-> {
            CallbackButton createButton = new CallbackButton("Create/Update", Callbacks.FILTER_EDIT);
            CallbackButton showAllButton = new CallbackButton("Show/Remove", Callbacks.FILTERS_SHOW);
            askFromInlineKeyboard("Please choose operation?", 2, createButton, showAllButton);
        };
        executeCommandOrAskToRegister(command);
    }
}
