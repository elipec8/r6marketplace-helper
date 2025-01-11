package github.ricemonger.notifications_service.services;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "private.notification", groupId = "r6s-helper-notifications-service")
    public void consumePrivateNotification(SendTextDTO sendTextDTO) {
        notificationService.sendPrivateNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "public.notification", groupId = "r6s-helper-notifications-service")
    public void consumePublicNotification(SendTextDTO sendTextDTO) {
        notificationService.sendPublicNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "public.all.users.notification", groupId = "r6s-helper-notifications-service")
    public void consumePublicNotificationToAllUsers(String message) {
        notificationService.sendPublicNotificationToAllUsers(message);
    }

    @KafkaListener(topics = "private.ubi_stats_updated.notification", groupId = "r6s-helper-notifications-service")
    public void consumeUbiStatsUpdateNotification(SendTextDTO sendTextDTO) {
        notificationService.sendUbiStatsUpdateNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "private.trade_manager.notification", groupId = "r6s-helper-notifications-service")
    public void consumeTradeManagerNotification(SendTextDTO sendTextDTO) {
        notificationService.sendTradeManagerNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "private.authorization.notification", groupId = "r6s-helper-notifications-service")
    public void consumeAuthorizationNotification(SendTextDTO sendTextDTO) {
        notificationService.sendAuthorizationNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }
}
