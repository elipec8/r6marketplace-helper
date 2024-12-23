package github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard("Enable trade manager active flag for new trade managers?",
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG_YES,
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG_NO);
    }
}
