package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemBuyEditStage5AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.generateTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagerTradeType.BUY),
                Callbacks.TRADES_EDIT_ONE_ITEM_BUY_FINISH,
                Callbacks.CANCEL);
    }
}
