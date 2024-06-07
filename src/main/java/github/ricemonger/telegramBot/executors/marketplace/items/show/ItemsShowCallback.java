package github.ricemonger.telegramBot.executors.marketplace.items.show;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton showByRequest = new CallbackButton("Show by Request", Callbacks.ITEMS_SHOW_BY_REQUEST);
        CallbackButton showBySettings = new CallbackButton("Show by Settings", Callbacks.ITEMS_SHOW_BY_SETTINGS);
        CallbackButton searchSettings = new CallbackButton("Search Settings", Callbacks.ITEMS_SEARCH_SETTINGS);
        CallbackButton displaySettings = new CallbackButton("Display Settings", Callbacks.ITEMS_DISPLAY_SETTINGS);
        askFromInlineKeyboard("Please choose operation?", 1, showByRequest, showBySettings, searchSettings, displaySettings);
    }
}
