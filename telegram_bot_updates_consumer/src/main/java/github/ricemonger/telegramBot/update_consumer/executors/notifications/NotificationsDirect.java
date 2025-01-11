package github.ricemonger.telegramBot.update_consumer.executors.notifications;

import github.ricemonger.marketplace.services.DTOs.NotificationsSettings;
import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class NotificationsDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        NotificationsSettings settings = botInnerService.getUserNotificationsSettings(updateInfo.getChatId());

        boolean privateFlag = settings.getPrivateNotificationsEnabledFlag() != null && settings.getPrivateNotificationsEnabledFlag();
        boolean publicFlag = settings.getPublicNotificationsEnabledFlag() != null && settings.getPublicNotificationsEnabledFlag();
        boolean ubiStatsUpdatedFlag = settings.getUbiStatsUpdatedNotificationsEnabledFlag() != null && settings.getUbiStatsUpdatedNotificationsEnabledFlag();
        boolean tradeManagerFlag = settings.getTradeManagerNotificationsEnabledFlag() != null && settings.getTradeManagerNotificationsEnabledFlag();
        boolean authorizationFlag = settings.getAuthorizationNotificationsEnabledFlag() != null && settings.getAuthorizationNotificationsEnabledFlag();

        StringBuilder text = new StringBuilder().append("Notifications settings:\n").
                append("Generic Private notifications: ").append(privateFlag ? "enabled" : "disabled").append("\n").
                append("Generic Public notifications: ").append(publicFlag ? "enabled" : "disabled").append("\n").
                append("Your Ubi account stats updated notifications: ").append(ubiStatsUpdatedFlag ? "enabled" : "disabled").append("\n").
                append("Trade manager notifications: ").append(tradeManagerFlag ? "enabled" : "disabled").append("\n").
                append("Authorization notifications: ").append(authorizationFlag ? "enabled" : "disabled");

        CallbackButton changePrivateFlag = new CallbackButton((privateFlag ? "Disable" : "Enable") + " private notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_PRIVATE_FLAG);
        CallbackButton changePublicFlag = new CallbackButton((publicFlag ? "Disable" : "Enable") + " public notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_PUBLIC_FLAG);
        CallbackButton changeUbiStatsUpdatedFlag = new CallbackButton((ubiStatsUpdatedFlag ? "Disable" : "Enable") + " Ubi stats updated notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_UBI_STATS_UPDATED_FLAG);
        CallbackButton changeTradeManagerFlag = new CallbackButton((tradeManagerFlag ? "Disable" : "Enable") + " trade manager notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_TRADE_MANAGER_FLAG);
        CallbackButton changeAuthorizationFlag = new CallbackButton((authorizationFlag ? "Disable" : "Enable") + " authorization notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_AUTHORIZATION_FLAG);

        askFromInlineKeyboard(text.toString(), 1, changePrivateFlag, changePublicFlag, changeUbiStatsUpdatedFlag, changeTradeManagerFlag, changeAuthorizationFlag);
    }
}
