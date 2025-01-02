package github.ricemonger.telegramBot.update_consumer.executors.itemFilters;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemFiltersDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> {
            CallbackButton createButton = new CallbackButton("Create/Update", Callbacks.ITEM_FILTER_EDIT);
            CallbackButton showAllButton = new CallbackButton("Show/Remove", Callbacks.ITEM_FILTERS_SHOW_ALL);
            askFromInlineKeyboard("Please choose operation?", 2, createButton, showAllButton);
        };
        executeCommandOrAskToRegister(command);
    }
}
