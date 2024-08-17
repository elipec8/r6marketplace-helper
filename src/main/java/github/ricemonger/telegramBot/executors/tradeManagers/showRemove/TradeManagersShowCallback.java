package github.ricemonger.telegramBot.executors.tradeManagers.showRemove;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton byItemFiltersButton = new CallbackButton("Trade Managers By Item Filters", Callbacks.TRADES_SHOW_BY_ITEM_FILTERS);
        CallbackButton byItemId = new CallbackButton("Trade Managers By Item Id", Callbacks.TRADES_SHOW_BY_ITEM_ID);
        askFromInlineKeyboard("Choose trade managers' type you want to search:", 2, byItemFiltersButton, byItemId);
    }
}
