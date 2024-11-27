package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeOperationType;

public class TradeByItemIdManagerBuyAndSellEditStage7AskConfirmationFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.generateTradeByItemIdManagerByUserInput(updateInfo.getChatId(),
                        TradeOperationType.BUY_AND_SELL),
                Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
