package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemBuyEditStage5AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.getPlannedOneItemTradeByUserInput(updateInfo.getChatId(), PlannedTradeType.BUY),
                Callbacks.TRADES_EDIT_ONE_ITEM_BUY_FINISH,
                Callbacks.CANCEL);
    }
}
