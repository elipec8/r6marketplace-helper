package github.ricemonger.telegramBot.executors.marketplace.tradeManagers.showRemove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;

import java.util.Collection;

public class TradeManagersByItemFiltersShowAllCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        Collection<TradeManagerByItemFilters> tradeManagers = botInnerService.getTradeManagersByItemFilters(updateInfo.getChatId());
    }
}
