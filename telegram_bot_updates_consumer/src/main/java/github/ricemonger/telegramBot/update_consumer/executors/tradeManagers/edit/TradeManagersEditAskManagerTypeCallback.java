package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersEditAskManagerTypeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton oneItem = new CallbackButton("One Item", Callbacks.TRADE_BY_ITEM_ID_MANAGER_EDIT);
        CallbackButton itemFilter = new CallbackButton("Item Filter", Callbacks.TRADE_BY_FILTERS_MANAGER_EDIT);
        askFromInlineKeyboard("Please select if the trade should be applied to only one item or by item filter:", 2, oneItem, itemFilter);
    }
}
