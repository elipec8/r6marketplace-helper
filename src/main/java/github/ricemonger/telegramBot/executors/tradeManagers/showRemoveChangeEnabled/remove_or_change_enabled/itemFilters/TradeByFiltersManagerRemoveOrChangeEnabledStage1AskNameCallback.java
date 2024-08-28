package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADE_BY_FILTERS_MANAGER_NAME,
                InputGroup.TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE,
                "Please enter name of trade manager you would like to look at:");
    }
}
