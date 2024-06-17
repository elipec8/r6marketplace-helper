package github.ricemonger.telegramBot.executors.marketplace;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class MarketplaceDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton itemsButton = new CallbackButton("Items", Callbacks.ITEMS);
        CallbackButton tradesButton = new CallbackButton("Trades", Callbacks.TRADES);
        CallbackButton filtersButton = new CallbackButton("Filters", Callbacks.FILTERS);
        askFromInlineKeyboard("Please choose marketplace operation type:",1, itemsButton,tradesButton,filtersButton);
    }
}
