package github.ricemonger.telegramBot.executors.marketplace.trades;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton createTrade = new CallbackButton("Create", Callbacks.TRADE_CREATE);
        CallbackButton showTrades = new CallbackButton("Show All", Callbacks.TRADES_SHOW_ALL);
        CallbackButton showSettings = new CallbackButton("Search Settings", Callbacks.TRADES_SEARCH_SETTINGS);
        CallbackButton managementSettings = new CallbackButton("Management Settings", Callbacks.TRADES_MANAGEMENT_SETTINGS);
        askFromInlineKeyboard("Please choose operation:",1, createTrade, showTrades, showSettings, managementSettings);
    }
}
