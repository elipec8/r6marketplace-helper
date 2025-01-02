package github.ricemonger.telegramBot.update_consumer.executors.items.settings;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String settings = botInnerService.getUserItemShowSettings(updateInfo.getChatId()).toHandsomeString();

        CallbackButton limit = new CallbackButton("Change Message Limit", Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT);
        CallbackButton itemsInMessage = new CallbackButton("Change Amount of items in Message", Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE);
        CallbackButton fields = new CallbackButton("Change Shown Item Fields", Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_SHOWN_FIELDS);
        CallbackButton filters = new CallbackButton("Change Applied Filters", Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_APPLIED_FILTERS);
        askFromInlineKeyboard("Current settings:\n" + settings + "\nWould you like to change them?", 1, limit, itemsInMessage, fields, filters);
    }
}
