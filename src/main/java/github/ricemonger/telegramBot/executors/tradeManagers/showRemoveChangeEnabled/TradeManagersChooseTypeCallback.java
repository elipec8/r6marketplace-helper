package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersChooseTypeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton byItemFiltersButton = new CallbackButton("Trade Managers By Item Filters", Callbacks.TRADE_BY_FILTERS_MANAGERS_SHOW_ALL);
        CallbackButton byItemId = new CallbackButton("Trade Managers By Item Id", Callbacks.TRADE_BY_ITEM_ID_MANAGERS_SHOW_ALL);
        askFromInlineKeyboard("Choose trade managers' type you want to search:", 2, byItemFiltersButton, byItemId);
    }
}
