package github.ricemonger.telegramBot.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeItemsInMessageCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard("""
                        Do you want to see few items in one message?
                        Picture of item will not be shown automatically by telegram,
                        but you will be able to get information about more items in one request""",
                Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_YES,
                Callbacks.ITEMS_SHOW_SETTINGS_CHANGE_FEW_ITEMS_IN_MESSAGE_CALLBACK_NO);
    }
}
