package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerRemoveStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE, "Please itemID of trade manager:");
    }
}
