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
        try {
            kafkaTemplate.send("private.notification", new SendTextDTO(userId, message));
        } catch (Exception e) {
            log.warn("Failed to send private notification to user {} cause of: ", userId, e);
        }
    }

    public void producePublicNotification(Long userId, String message) {
        log.info("Producing public notification for user with id: " + userId + " with message: " + message);
        try {
            kafkaTemplate.send("public.notification", new SendTextDTO(userId, message));
        } catch (Exception e) {
            log.warn("Failed to send public notification to user {} cause of: ", userId, e);
        }
    }

    public void producePublicNotificationToAllUsers(String message) {
        log.info("Producing public notification for all users with message: " + message);
        try {
            kafkaTemplate.send("public.all.users.notification", message);
        } catch (Exception e) {
            log.warn("Failed to send public notification no all users cause of: ", e);
        }
    }

    public void produceUbiStatsUpdatedNotification(Long userId, String string) {
        log.info("Producing ubi stats updated notification for user with id: " + userId + " with message: " + string);
        try {
            kafkaTemplate.send("private.ubi_stats_updated.notification", new SendTextDTO(userId, string));
        } catch (Exception e) {
            log.warn("Failed to send ubi stats updated notification to user {} cause of: ", userId, e);
        }
    }

    public void produceTradeManagerNotification(Long userId, String string) {
        log.info("Producing trade manager notification for user with id: " + userId + " with message: " + string);
        try {
            kafkaTemplate.send("private.trade_manager.notification", new SendTextDTO(userId, string));
        } catch (Exception e) {
            log.warn("Failed to send trade manager notification to user {} cause of: ", userId, e);
        }
    }

    public void produceAuthorizationNotification(Long userId, String string) {
        log.info("Producing authorization notification for user with id: " + userId + " with message: " + string);
        try {
            kafkaTemplate.send("private.authorization.notification", new SendTextDTO(userId, string));
        } catch (Exception e) {
            log.warn("Failed to send authorization notification to user {} cause of: ", userId, e);
        }
    }
}
