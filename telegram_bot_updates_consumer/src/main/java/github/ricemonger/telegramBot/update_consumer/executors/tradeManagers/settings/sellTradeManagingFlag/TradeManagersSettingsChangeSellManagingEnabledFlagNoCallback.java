package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradeManagingFlag;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeSellManagingEnabledFlagNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSellSettingsManagingEnabledFlag(updateInfo.getChatId(), false);
        sendText("Automatic sell orders trading via managers is disabled.");
    }
}
