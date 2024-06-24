package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.itemFilter;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesItemFilterEditStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADES_EDIT_ITEM_FILTER_TRADE_NAME,
                InputGroup.TRADES_EDIT_ITEM_FILTER,
                "Please enter unused name to create new Trade\nOR\nEnter used name to edit existing one:");
    }
}
