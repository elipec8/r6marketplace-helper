package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.managingEnabledFlag;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeManagingEnabledFlagYesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSettingsManagingEnabledFlag(updateInfo.getChatId(), true);
        sendText("Automatic trading via managers is enabled.");
    }
}
