package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemEditCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton buy = new CallbackButton("Buy", Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_BUY);
        CallbackButton sell = new CallbackButton("Sell", Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_SELL);
        CallbackButton buyAndSell = new CallbackButton("Buy and sell", Callbacks.TRADES_EDIT_ONE_ITEM_TYPE_BUY_AND_SELL);
        askFromInlineKeyboard("Please select if item should be only bought, sold or constantly bought and resold:",2, buy, sell, buyAndSell);
    }
}
