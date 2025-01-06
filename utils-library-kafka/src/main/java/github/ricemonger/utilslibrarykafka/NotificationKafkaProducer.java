package github.ricemonger.utilslibrarykafka;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void producePrivateNotification(Long userId, String message) {
        log.info("Producing private notification for user with id: " + userId + " with message: " + message);
        kafkaTemplate.send("private.notification", new SendTextDTO(userId, message));
    }

    public void producePublicNotification(Long userId, String message) {
        log.info("Producing public notification for user with id: " + userId + " with message: " + message);
        kafkaTemplate.send("public.notification", new SendTextDTO(userId, message));
    }

    public void producePublicNotificationToAllUsers(String message) {
        log.info("Producing public notification for all users with message: " + message);
        kafkaTemplate.send("public.all.users.notification", message);
    }
}
