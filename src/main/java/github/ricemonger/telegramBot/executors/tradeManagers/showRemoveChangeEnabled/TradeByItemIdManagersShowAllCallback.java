package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByItemIdManager;

import java.util.Collection;

public class TradeByItemIdManagersShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeByItemIdManager> tradeManagers = botInnerService.getAllUserTradeByItemIdManagers(updateInfo.getChatId());

        botInnerService.sendMultipleObjectStringsGroupedInMessages(tradeManagers, 9, updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Do you want to remove or activate/deactivate any of these trade managers?",
                Callbacks.TRADE_BY_ITEM_ID_MANAGER_REMOVE_OR_ENABLED_CHANGE,
                Callbacks.CANCEL);
    }
}
