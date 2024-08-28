package github.ricemonger.telegramBot.executors.tradeManagers.settings;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeManagersSettings;

public class TradeManagersSettingsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        TradeManagersSettings settings = botInnerService.getUserTradeManagersSettings(updateInfo.getChatId());

        CallbackButton newActiveFlag = new CallbackButton("Change New Managers Are Active Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG);
        CallbackButton managingEnabledFlag = new CallbackButton("Change Managing Enabled Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG);

        askFromInlineKeyboard("Current settings:\n" + settings + "\nWould you like to change them?", 1, newActiveFlag, managingEnabledFlag);
    }
}
