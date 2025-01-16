package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradeManagingFlag;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeSellManagingEnabledFlagYesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSellSettingsManagingEnabledFlag(updateInfo.getChatId(), true);
        sendText("Automatic sell orders trading via managers is enabled.");
    }
}
