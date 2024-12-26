package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerEditStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADE_BY_FILTERS_MANAGER_NAME,
                InputGroup.TRADE_BY_FILTERS_MANAGER_EDIT,
                "Please enter unused name to create new Trade\nOR\nEnter used name to edit existing one:");
    }
}
