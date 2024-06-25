package github.ricemonger.telegramBot.executors.marketplace.items.settings.shownFields;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputState;

public class ItemsShowSettingsChangeShownFieldsStage7AskPictureFlagInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_PICTURE);

        askYesOrNoFromInlineKeyboard("Do you want to show picture?", Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
