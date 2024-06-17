package github.ricemonger.telegramBot.executors.marketplace.items;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton showBySettings = new CallbackButton("Show Items by Settings", Callbacks.ITEMS_SHOW);
        CallbackButton searchSettings = new CallbackButton("Search and Display Settings", Callbacks.ITEMS_SHOW_SETTINGS);
        askFromInlineKeyboard("Please choose operation?", 1, showBySettings, searchSettings);
    }
}
