package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeByItemIdManagerBuyEditStage1AskItemIdCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID, InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT, "Please enter Id of managed item:");
    }
}
