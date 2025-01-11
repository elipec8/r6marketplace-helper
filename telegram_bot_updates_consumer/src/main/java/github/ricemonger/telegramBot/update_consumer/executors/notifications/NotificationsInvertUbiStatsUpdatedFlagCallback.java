package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsInvertUbiStatsUpdatedFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserUbiStatsUpdatedNotificationsFlag(updateInfo.getChatId());
        sendText("Ubi stats updated notifications flag has been changed");
    }
}
