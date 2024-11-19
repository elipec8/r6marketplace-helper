package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeOperationType;

public class TradeByItemIdManagerBuyEditStage5AskConfirmationFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.generateTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeOperationType.BUY),
                Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_EDIT_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
