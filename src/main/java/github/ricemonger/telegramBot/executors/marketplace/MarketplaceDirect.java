package github.ricemonger.telegramBot.executors.marketplace;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class MarketplaceDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton speculativeItemsButton = new CallbackButton("Speculative items", Callbacks.SPECULATIVE_ITEMS);
        askFromInlineKeyboard("Please choose marketplace operation type:",1, speculativeItemsButton);
    }
}
