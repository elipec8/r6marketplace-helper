package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesEditCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton oneItem = new CallbackButton("One Item", Callbacks.TRADES_EDIT_ONE_ITEM);
        CallbackButton itemFilter = new CallbackButton("Item Filter", Callbacks.TRADES_EDIT_ITEM_FILTER);
        askFromInlineKeyboard("Please select if the trade should be applied to only one item or by item filter:", 2, oneItem, itemFilter);
    }
}
