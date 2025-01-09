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

        String text = "Notifications settings:\n" +
                      "Private flag: " + (privateFlag ? "enabled" : "disabled") + "\n" +
                      "Public flag: " + (publicFlag ? "enabled" : "disabled");

        CallbackButton changePrivateFlag = new CallbackButton((privateFlag ? "Disable" : "Enable") + " private notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_PRIVATE_FLAG);
        CallbackButton changePublicFlag = new CallbackButton((publicFlag ? "Disable" : "Enable") + " public notifications", Callbacks.NOTIFICATIONS_SETTINGS_INVERT_PUBLIC_FLAG);

        askFromInlineKeyboard(text, 1, changePrivateFlag, changePublicFlag);
    }
}
