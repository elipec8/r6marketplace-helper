package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeByFiltersManager;

import java.util.Collection;

public class TradeManagersByItemFiltersShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeByFiltersManager> tradeManagers = botInnerService.getAllUserTradeByFiltersManagers(updateInfo.getChatId());
    }
}
