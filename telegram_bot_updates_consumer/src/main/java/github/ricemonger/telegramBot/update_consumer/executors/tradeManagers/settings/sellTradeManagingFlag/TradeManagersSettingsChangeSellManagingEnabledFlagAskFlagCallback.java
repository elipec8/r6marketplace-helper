package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradeManagingFlag;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeSellManagingEnabledFlagAskFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard("Enable automatic sell orders trading via managers?",
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_MANAGING_ENABLED_FLAG_YES,
                Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_MANAGING_ENABLED_FLAG_NO);
    }
}
