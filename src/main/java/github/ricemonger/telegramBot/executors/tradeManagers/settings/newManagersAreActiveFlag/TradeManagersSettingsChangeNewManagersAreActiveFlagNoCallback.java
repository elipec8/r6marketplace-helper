package github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSettingsNewManagersAreActiveFlag(updateInfo.getChatId(),false);
        sendText("New managers will not be activated automatically.");
    }
}
