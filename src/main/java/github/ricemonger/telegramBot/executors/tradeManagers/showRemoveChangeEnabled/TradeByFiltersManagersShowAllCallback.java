package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.DTOs.personal.TradeByFiltersManager;

import java.util.Collection;

public class TradeByFiltersManagersShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeByFiltersManager> tradeManagers = botInnerService.getAllUserTradeByFiltersManagers(updateInfo.getChatId());

        botInnerService.sendMultipleObjectStringsGroupedInMessages(tradeManagers, 9, updateInfo.getChatId());

        askYesOrNoFromInlineKeyboard(
                "Do you want to remove or activate/deactivate any of these trade managers?",
                Callbacks.TRADE_BY_FILTERS_MANAGER_REMOVE_OR_ENABLED_CHANGE,
                Callbacks.CANCEL);
    }
}
