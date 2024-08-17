package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove.itemId;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByItemIdManager;

public class TradeManagersByItemIdRemoveStage2AskConfirmationInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        TradeByItemIdManager toRemove = botInnerService.getUserTradeByItemIdManagerByUserInputItemId(updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Are you sure you want to remove chosen trade manager:\n" + toRemove,
                Callbacks.TRADES_REMOVE_BY_ITEM_ID_FINISH,
                Callbacks.CANCEL);
    }
}
