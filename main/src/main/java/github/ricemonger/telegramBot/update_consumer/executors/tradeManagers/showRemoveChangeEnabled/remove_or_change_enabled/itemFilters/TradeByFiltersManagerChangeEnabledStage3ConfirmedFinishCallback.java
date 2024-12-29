package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserTradeByFiltersManagerEnabledByUserInput(updateInfo.getChatId());
        sendText("Trade manager enabled flag was successfully changed");
    }
}
