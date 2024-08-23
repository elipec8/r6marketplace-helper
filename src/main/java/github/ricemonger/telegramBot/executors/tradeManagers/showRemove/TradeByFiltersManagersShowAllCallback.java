package github.ricemonger.telegramBot.executors.tradeManagers.showRemove;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByFiltersManager;

import java.util.Collection;

public class TradeByFiltersManagersShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeByFiltersManager> tradeManagers = botInnerService.getAllUserTradeByFiltersManagers(updateInfo.getChatId());

        botInnerService.sendMultipleObjectStringsGroupedInMessages(tradeManagers, 8, updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Do you want to remove any of these trade managers?",
                Callbacks.TRADE_BY_FILTERS_MANAGER_REMOVE,
                Callbacks.CANCEL_SILENT);
    }
}
