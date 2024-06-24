package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesOneItemBuyEditStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, InputGroup.TRADES_EDIT_ONE_ITEM_BUY, "Please enter Id of managed item:");
    }
}
