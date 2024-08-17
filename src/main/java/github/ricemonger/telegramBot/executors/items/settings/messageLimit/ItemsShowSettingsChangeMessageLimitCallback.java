package github.ricemonger.telegramBot.executors.items.settings.messageLimit;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeMessageLimitCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT,
                InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT,
                "Enter limit of messages, sent as response to each request(1-50):");
    }
}
