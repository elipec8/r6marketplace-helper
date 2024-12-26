package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerEditStage2AskTypeInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADE_BY_FILTERS_MANAGER_TRADE_TYPE);
        CallbackButton buy = new CallbackButton("Buy", Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_BUY_EDIT);
        CallbackButton sell = new CallbackButton("Sell", Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_SELL_EDIT);
        CallbackButton buyAndSell = new CallbackButton("Buy and sell", Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_BUY_AND_SELL_EDIT);
        askFromInlineKeyboard("Please select if items should be only bought, sold or constantly bought and resold:", 2, buy, sell, buyAndSell);
    }
}
