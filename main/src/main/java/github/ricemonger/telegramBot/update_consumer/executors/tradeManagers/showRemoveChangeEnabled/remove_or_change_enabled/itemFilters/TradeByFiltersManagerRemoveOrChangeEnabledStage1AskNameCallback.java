package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeByFiltersManagerRemoveOrChangeEnabledStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(
                InputState.TRADE_BY_FILTERS_MANAGER_NAME,
                InputGroup.TRADE_BY_FILTERS_MANAGER_SHOW_OR_REMOVE,
                "Please enter name of trade manager you would like to look at:");
    }
}
