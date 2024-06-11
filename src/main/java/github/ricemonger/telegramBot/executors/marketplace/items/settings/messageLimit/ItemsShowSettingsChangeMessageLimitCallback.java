package github.ricemonger.telegramBot.executors.marketplace.items.settings.messageLimit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class ItemsShowSettingsChangeMessageLimitCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.ITEMS_SHOW_SETTING_MESSAGE_LIMIT,
                InputGroup.ITEMS_SHOW_SETTINGS_CHANGE_MESSAGE_LIMIT,
                "Enter limit of messages, sent as response to each request");
    }
}
