package github.ricemonger.utilslibrarykafka;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void producePrivateNotification(Long userId, String message) {
        kafkaTemplate.send("private.notification", new SendTextDTO(userId, message));
    }

    public void producePublicNotification(Long userId, String message) {
        kafkaTemplate.send("public.notification", new SendTextDTO(userId, message));
    }

    public void producePublicNotificationToAllUsers(String message) {
        kafkaTemplate.send("public.all.users.notification", message);
    }
}
