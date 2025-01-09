package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsInvertPrivateFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserPrivateNotificationsFlag(updateInfo.getChatId());
        sendText("Private notifications flag has been changed");
    }
}
