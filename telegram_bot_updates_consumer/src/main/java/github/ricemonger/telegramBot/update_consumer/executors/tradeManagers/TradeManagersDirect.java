package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> {
            CallbackButton createTrade = new CallbackButton("Create/Update", Callbacks.TRADE_MANAGERS_EDIT);
            CallbackButton showTrades = new CallbackButton("Show/Remove", Callbacks.TRADE_MANAGERS_SHOW_CHOOSE_TYPE);
            CallbackButton showSettings = new CallbackButton("Settings", Callbacks.TRADE_MANAGERS_SETTINGS);
            askFromInlineKeyboard("Please choose operation:", 1, createTrade, showTrades, showSettings);
        };
        executeCommandOrAskToRegister(command);
    }
}
