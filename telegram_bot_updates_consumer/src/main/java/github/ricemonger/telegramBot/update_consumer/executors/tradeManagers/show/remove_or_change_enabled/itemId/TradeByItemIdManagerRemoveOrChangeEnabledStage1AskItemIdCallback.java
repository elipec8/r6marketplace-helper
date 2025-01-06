package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID,
                InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE,
                "Please enter itemID of trade manager:");
    }
}
