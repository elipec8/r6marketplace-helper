package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersByItemIdRemoveStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, InputGroup.TRADES_REMOVE_ITEM_ID, "Please itemID of trade manager:");
    }
}
