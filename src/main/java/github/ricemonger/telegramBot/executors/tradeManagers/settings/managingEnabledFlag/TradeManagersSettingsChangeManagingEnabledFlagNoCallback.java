package github.ricemonger.telegramBot.executors.tradeManagers.settings.managingEnabledFlag;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeManagingEnabledFlagNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSettingsManagingEnabledFlag(updateInfo.getChatId(),false);
        sendText("Automatic trading via managers is disabled.");
    }
}
