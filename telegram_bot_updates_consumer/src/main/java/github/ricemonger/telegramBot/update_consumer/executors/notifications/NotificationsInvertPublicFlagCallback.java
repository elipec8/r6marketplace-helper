package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsInvertPublicFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserPublicNotificationsFlag(updateInfo.getChatId());
        sendText("Public notifications flag has been changed");
    }
}
