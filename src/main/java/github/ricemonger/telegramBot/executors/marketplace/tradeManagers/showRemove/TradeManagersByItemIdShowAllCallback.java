package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByItemIdManager;

import java.util.Collection;

public class TradeManagersByItemIdShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeByItemIdManager> tradeManagers = botInnerService.getAllUserTradeByItemIdManagers(updateInfo.getChatId());

        botInnerService.sendMultipleObjectStringsGroupedInMessages(tradeManagers, 8, updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Do you want to remove any of these trade managers?",
                Callbacks.TRADES_REMOVE_BY_ITEM_ID,
                Callbacks.SILENT_CANCEL);
    }
}
