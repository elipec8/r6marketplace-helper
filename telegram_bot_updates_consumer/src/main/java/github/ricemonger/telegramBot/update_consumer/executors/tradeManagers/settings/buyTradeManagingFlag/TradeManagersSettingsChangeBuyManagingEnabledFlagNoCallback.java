package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradeManagingFlag;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeBuyManagingEnabledFlagNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersBuySettingsManagingEnabledFlag(updateInfo.getChatId(), false);
        sendText("Automatic buy orders trading via managers is disabled.");
    }
}
