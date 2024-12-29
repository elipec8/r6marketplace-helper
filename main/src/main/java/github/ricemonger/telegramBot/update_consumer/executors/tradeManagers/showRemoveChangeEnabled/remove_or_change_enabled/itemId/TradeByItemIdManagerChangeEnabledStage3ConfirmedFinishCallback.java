package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeByItemIdManagerChangeEnabledStage3ConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserTradeByItemIdManagerEnabledByUserInput(updateInfo.getChatId());
        sendText("Trade manager enabled flag was successfully changed");
    }
}
