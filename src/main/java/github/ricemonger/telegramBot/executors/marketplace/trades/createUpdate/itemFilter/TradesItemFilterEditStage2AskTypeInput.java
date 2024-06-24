package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.itemFilter;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesItemFilterEditStage2AskTypeInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ITEM_FILTER_TRADE_TYPE);
        CallbackButton buy = new CallbackButton("Buy", Callbacks.TRADES_EDIT_ITEM_FILTER_TRADE_TYPE_BUY);
        CallbackButton sell = new CallbackButton("Sell", Callbacks.TRADES_EDIT_ITEM_FILTER_TRADE_TYPE_SELL);
        CallbackButton buyAndSell = new CallbackButton("Buy and sell", Callbacks.TRADES_EDIT_ITEM_FILTER_TRADE_TYPE_BUY_AND_SELL);
        askFromInlineKeyboard("Please select if items should be only bought, sold or constantly bought and resold:",2, buy, sell, buyAndSell);
    }
}
