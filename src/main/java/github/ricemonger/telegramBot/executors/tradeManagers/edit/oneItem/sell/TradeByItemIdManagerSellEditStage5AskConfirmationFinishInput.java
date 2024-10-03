package github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagingType;

public class TradeByItemIdManagerSellEditStage5AskConfirmationFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.generateTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagingType.SELL),
                Callbacks.TRADE_BY_ITEM_ID_MANAGER_TYPE_SELL_EDIT_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
