package github.ricemonger.telegramBot.update_consumer.executors.items.settings.shownFields;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME, InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS);

        askYesOrNoFromInlineKeyboard("Do you want to show item name?", Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
