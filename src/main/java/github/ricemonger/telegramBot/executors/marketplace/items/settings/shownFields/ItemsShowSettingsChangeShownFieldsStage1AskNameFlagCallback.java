package github.ricemonger.telegramBot.executors.marketplace.items.settings.shownFields;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_NAME,InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS);

        askYesOrNoFromInlineKeyboard("Do you want to show item name?", Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
