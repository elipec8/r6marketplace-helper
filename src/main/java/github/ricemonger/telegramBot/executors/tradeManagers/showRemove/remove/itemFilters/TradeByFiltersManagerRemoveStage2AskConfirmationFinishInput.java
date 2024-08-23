package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByFiltersManager;

public class TradeByFiltersManagerRemoveStage2AskConfirmationFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        TradeByFiltersManager tradeManager = botInnerService.getUserTradeByFiltersManagerByUserInputName(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Do you wand to remove chosen trade manager?:\n\n" + tradeManager,
                Callbacks.TRADE_BY_FILTERS_MANAGER_REMOVE_FINISH_CONFIRMED,
                Callbacks.CANCEL_SILENT);
    }
}
