package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.itemFilter;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradesItemFilterEditStage3AskFiltersInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.TRADES_EDIT_ITEM_FILTER_FILTERS_NAMES);

        sendText("List of available filters:\n"
                 + botInnerService.getAllFilterNamesForUser(updateInfo.getChatId())
                 + "\nPlease enter names of item filters, you want to apply to the trade, divided by \",\" or \"|\":");
    }
}
