package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerEditAskTradeTypeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton buy = new CallbackButton("Buy", Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT);
        CallbackButton sell = new CallbackButton("Sell", Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT);
        CallbackButton buyAndSell = new CallbackButton("Buy and sell", Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT);
        askFromInlineKeyboard("Please select if item should be only bought, sold or constantly bought and resold:", 2, buy, sell, buyAndSell);
    }
}
