package github.ricemonger.telegramBot.executors.marketplace.tradeManagers;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton createTrade = new CallbackButton("Create/Update", Callbacks.TRADE_CREATE_OR_UPDATE);
        CallbackButton showTrades = new CallbackButton("Show/Remove", Callbacks.TRADES_SHOW_OR_REMOVE);
        CallbackButton showSettings = new CallbackButton("Settings", Callbacks.TRADES_SETTINGS);
        askFromInlineKeyboard("Please choose operation:",1, createTrade, showTrades, showSettings);
    }
}
