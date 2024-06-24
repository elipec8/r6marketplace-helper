package github.ricemonger.telegramBot.executors.marketplace.trades.createUpdate.oneItem.sell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.PlannedTradeType;

public class TradesOneItemSellEditStage5AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.getPlannedOneItemTradeByUserInput(updateInfo.getChatId(), PlannedTradeType.SELL),
                Callbacks.TRADES_EDIT_ONE_ITEM_SELL_FINISH,
                Callbacks.CANCEL);
    }
}
