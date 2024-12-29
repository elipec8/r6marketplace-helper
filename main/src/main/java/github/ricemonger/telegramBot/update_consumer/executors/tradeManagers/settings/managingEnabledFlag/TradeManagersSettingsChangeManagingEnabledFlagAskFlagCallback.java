package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.managingEnabledFlag;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeManagingEnabledFlagAskFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard("Enable automatic trading via managers?",
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG_YES,
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG_NO);
    }
}
