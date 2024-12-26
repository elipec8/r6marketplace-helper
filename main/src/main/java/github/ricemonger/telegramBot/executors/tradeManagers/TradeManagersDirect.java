package github.ricemonger.telegramBot.executors.tradeManagers;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton createTrade = new CallbackButton("Create/Update", Callbacks.TRADE_MANAGERS_EDIT);
        CallbackButton showTrades = new CallbackButton("Show/Remove", Callbacks.TRADE_MANAGERS_SHOW_CHOOSE_TYPE);
        CallbackButton showSettings = new CallbackButton("Settings", Callbacks.TRADE_MANAGERS_SETTINGS);
        askFromInlineKeyboard("Please choose operation:", 1, createTrade, showTrades, showSettings);
    }
}
