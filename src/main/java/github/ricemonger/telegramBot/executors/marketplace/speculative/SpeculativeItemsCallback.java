package github.ricemonger.telegramBot.executors.marketplace.speculative;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class SpeculativeItemsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton showAllButton = new CallbackButton("Show All", Callbacks.SPECULATIVE_ITEMS_SHOW_ALL);
        CallbackButton showOwnedButton = new CallbackButton("Show Owned", Callbacks.SPECULATIVE_ITEMS_SHOW_OWNED);
        askFromInlineKeyboard("Please choose operation:",1, showAllButton, showOwnedButton);
    }
}
