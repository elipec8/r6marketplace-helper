package github.ricemonger.telegramBot.executors.marketplace.items.show.settings;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String settings = botInnerService.getItemShowSettingsForUser(updateInfo.getChatId());

        CallbackButton limit = new CallbackButton("Change Limit", Callbacks.ITEM_SHOW_SETTINGS_CHANGE_LIMIT);
        CallbackButton fields = new CallbackButton("Change Shown Fields", Callbacks.ITEM_SHOW_SETTINGS_CHANGE_FIELDS);
        CallbackButton filters = new CallbackButton("Change Applied Filters", Callbacks.ITEM_SHOW_SETTINGS_CHANGE_FILTERS);
        askFromInlineKeyboard("Current settings:\n" + settings + "\nWould you like to change them?",1,limit,fields,filters);
    }
}
