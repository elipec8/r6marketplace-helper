package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID,
                InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE,
                "Please enter itemID of trade manager:");
    }
}
