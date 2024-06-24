package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.buySell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemBuyOrSellEditStage7AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.getPlannedOneItemTradeByUserInput(updateInfo.getChatId(),
                        PlannedTradeType.BUY_AND_SELL),
                Callbacks.TRADES_EDIT_ONE_ITEM_BUY_AND_SELL_FINISH,
                Callbacks.CANCEL);
    }
}
