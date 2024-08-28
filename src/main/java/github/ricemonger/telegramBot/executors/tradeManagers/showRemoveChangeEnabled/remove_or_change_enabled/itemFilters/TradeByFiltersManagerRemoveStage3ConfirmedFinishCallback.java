package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerRemoveStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserTradeByFiltersManagerByUserInput(updateInfo.getChatId());
        sendText("Trade manager successfully removed");
    }
}
