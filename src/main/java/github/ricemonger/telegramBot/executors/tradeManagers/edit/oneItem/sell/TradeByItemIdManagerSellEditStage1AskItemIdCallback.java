package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerSellEditStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID, InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT, "Please enter Id of managed item:");
    }
}
