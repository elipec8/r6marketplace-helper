package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeManagerByItemId;

public class TradeManagersByItemIdRemoveStage2AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        TradeManagerByItemId toRemove = botInnerService.getTradeManagerByItemIdByUserInput(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Are you sure you want to remove chosen trade manager:\n" + toRemove,
                Callbacks.TRADES_REMOVE_BY_ITEM_ID_FINISH,
                Callbacks.CANCEL);
    }
}
