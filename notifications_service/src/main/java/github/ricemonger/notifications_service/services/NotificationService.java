package github.ricemonger.notifications_service.services;

import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserDatabaseService userDatabaseService;

    private final TelegramBotClientService telegramBotClientService;

    public void sendPrivateNotificationToUser(Long userId, String text) {
        ToBeNotifiedUser user = userDatabaseService.getToBeNotifiedUser(userId);

        if (user.privateNotificationsEnabled() != null && user.privateNotificationsEnabled()) {
            telegramBotClientService.sendText(user.chatId(), text);
        }
    }

    public void sendPublicNotificationToUser(Long userId, String text) {
        ToBeNotifiedUser user = userDatabaseService.getToBeNotifiedUser(userId);

        if (user.publicNotificationsEnabled() != null && user.publicNotificationsEnabled()) {
            telegramBotClientService.sendText(user.chatId(), text);
        }
    }

    public void sendPublicNotificationToAllUsers(String text) {
        List<ToBeNotifiedUser> users = userDatabaseService.getAllToBeNotifiedUsers();

        for (ToBeNotifiedUser user : users) {
            if (user.publicNotificationsEnabled() != null && user.publicNotificationsEnabled()) {
                telegramBotClientService.sendText(user.chatId(), text);
            }
        }
    }

    public void sendUbiStatsUpdateNotificationToUser(Long userId, String text) {
        ToBeNotifiedUser user = userDatabaseService.getToBeNotifiedUser(userId);

        if (user.ubiStatsUpdateNotificationsEnabled() != null && user.ubiStatsUpdateNotificationsEnabled()) {
            telegramBotClientService.sendText(user.chatId(), text);
        }
    }

    public void sendTradeManagerNotificationToUser(Long userId, String text) {
        ToBeNotifiedUser user = userDatabaseService.getToBeNotifiedUser(userId);

        if (user.tradeManagerNotificationsEnabled() != null && user.tradeManagerNotificationsEnabled()) {
            telegramBotClientService.sendText(user.chatId(), text);
        }
    }

    public void sendAuthorizationNotificationToUser(Long userId, String text) {
        ToBeNotifiedUser user = userDatabaseService.getToBeNotifiedUser(userId);

        if (user.authorizationNotificationsEnabled() != null && user.authorizationNotificationsEnabled()) {
            telegramBotClientService.sendText(user.chatId(), text);
        }
    }
}
