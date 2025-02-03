package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings;

import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        TradeManagersSettings settings = botInnerService.getUserTradeManagersSettings(updateInfo.getChatId());

        CallbackButton newActiveFlag = new CallbackButton("Change New Managers Are Active Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_NEW_MANAGERS_ARE_ACTIVE_FLAG);
        CallbackButton managingEnabledFlag = new CallbackButton("Change Managing Enabled Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_MANAGING_ENABLED_FLAG);
        CallbackButton sellManagerEnabledFlag = new CallbackButton("Change Sell Manage Enabled Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_MANAGING_ENABLED_FLAG);
        CallbackButton sellTradePriorityExpression = new CallbackButton("Change Sell Trade Priority Expression", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_TRADE_PRIORITY_EXPRESSION);
        CallbackButton buyManagerEnabledFlag = new CallbackButton("Change Buy Manage Enabled Flag", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_MANAGING_ENABLED_FLAG);
        CallbackButton buyTradePriorityExpression = new CallbackButton("Change Buy Trade Priority Expression", Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION);

        askFromInlineKeyboard("Current settings:\n" + settings.toHandsomeString() + "\nWould you like to change them?", 2,
                newActiveFlag, managingEnabledFlag,
                sellManagerEnabledFlag, sellTradePriorityExpression,
                buyManagerEnabledFlag, buyTradePriorityExpression);
    }
}
