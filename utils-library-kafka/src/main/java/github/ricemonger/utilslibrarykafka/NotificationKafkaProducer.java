package github.ricemonger.utilslibrarykafka;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "private.notification", groupId = "r6s-helper-notifications-service")
    public void producePrivateNotification(Long userId, String message) {
        kafkaTemplate.send("private.notification", new SendTextDTO(userId, message));
    }

    @KafkaListener(topics = "public.notification", groupId = "r6s-helper-notifications-service")
    public void producePublicNotification(Long userId, String message) {
        kafkaTemplate.send("public.notification", new SendTextDTO(userId, message));
    }

    @KafkaListener(topics = "public.all.users.notification", groupId = "r6s-helper-notifications-service")
    public void producePublicNotificationToAllUsers(String message) {
        kafkaTemplate.send("public.all.users.notification", message);
    }
}
