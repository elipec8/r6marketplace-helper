package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsInvertAuthorizationFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserAuthorizationNotificationsFlag(updateInfo.getChatId());
        sendText("Authorization notifications flag has been changed");
    }
}
