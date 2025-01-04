package github.ricemonger.notifications_service.services;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "private.notification")
    public void consumePrivateNotification(SendTextDTO sendTextDTO) {
        notificationService.sendPrivateNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "public.notification")
    public void consumePublicNotification(SendTextDTO sendTextDTO) {
        notificationService.sendPublicNotificationToUser(sendTextDTO.userId(), sendTextDTO.text());
    }

    @KafkaListener(topics = "public.all.users.notification")
    public void consumePublicNotificationToAllUsers(String message) {
        notificationService.sendPublicNotificationToAllUsers(message);
    }
}
