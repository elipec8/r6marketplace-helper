package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.buyAndSell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemBuyAndSellEditStage7AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.getUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(),
                        TradeManagerTradeType.BUY_AND_SELL),
                Callbacks.TRADES_EDIT_ONE_ITEM_BUY_AND_SELL_FINISH,
                Callbacks.CANCEL);
    }
}
