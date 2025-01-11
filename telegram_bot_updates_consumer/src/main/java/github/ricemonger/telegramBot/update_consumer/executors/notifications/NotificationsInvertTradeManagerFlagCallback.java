package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsInvertTradeManagerFlagCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.invertUserTradeManagerNotificationsFlag(updateInfo.getChatId());
        sendText("Trade manager notifications flag has been changed");
    }
}
