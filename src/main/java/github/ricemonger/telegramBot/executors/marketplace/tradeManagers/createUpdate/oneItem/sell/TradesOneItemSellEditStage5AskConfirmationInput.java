package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.createUpdate.oneItem.sell;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.TradeManagerTradeType;

public class TradesOneItemSellEditStage5AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        askYesOrNoFromInlineKeyboard(
                "Do you want to confirm and save the trade?\n" + botInnerService.getUserTradeByItemIdManagerByUserInput(updateInfo.getChatId(), TradeManagerTradeType.SELL),
                Callbacks.TRADES_EDIT_ONE_ITEM_SELL_FINISH,
                Callbacks.CANCEL);
    }
}
