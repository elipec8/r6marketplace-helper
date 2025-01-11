package github.ricemonger.utilslibrarykafka;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class NotificationKafkaProducerTest {

    private final KafkaTemplate<String, Object> kafkaTemplate = mock(KafkaTemplate.class);

    private final NotificationKafkaProducer notificationKafkaProducer = new NotificationKafkaProducer(kafkaTemplate);

    @Test
    void producePrivateNotification_should_handle_to_template() {
        notificationKafkaProducer.producePrivateNotification(1L, "Test message");

        verify(kafkaTemplate).send("private.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void producePrivateNotification_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.producePrivateNotification(1L, "Test message"));
    }

    @Test
    void producePublicNotification_should_handle_to_template() {
        notificationKafkaProducer.producePublicNotification(1L, "Test message");

        verify(kafkaTemplate).send("public.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void producePublicNotification_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.producePublicNotification(1L, "Test message"));
    }

    @Test
    void producePublicNotificationToAllUsers_should_handle_to_template() {
        notificationKafkaProducer.producePublicNotificationToAllUsers("Test message");

        verify(kafkaTemplate).send("public.all.users.notification", "Test message");
    }

    @Test
    void producePublicNotificationToAllUsers_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.producePublicNotificationToAllUsers("Test message"));
    }

    @Test
    void produceUbiStatsUpdatedNotification_should_handle_to_template() {
        notificationKafkaProducer.produceUbiStatsUpdatedNotification(1L, "Test message");

        verify(kafkaTemplate).send("private.ubi_stats_updated.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void produceUbiStatsUpdatedNotification_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.produceUbiStatsUpdatedNotification(1L, "Test message"));
    }

    @Test
    void produceTradeManagerNotification_should_handle_to_template() {
        notificationKafkaProducer.produceTradeManagerNotification(1L, "Test message");

        verify(kafkaTemplate).send("private.trade_manager.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void produceTradeManagerNotification_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.produceTradeManagerNotification(1L, "Test message"));
    }

    @Test
    void produceAuthorizationNotification_should_handle_to_template() {
        notificationKafkaProducer.produceAuthorizationNotification(1L, "Test message");

        verify(kafkaTemplate).send("private.authorization.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void produceAuthorizationNotification_should_handle_exception_in_template() {
        when(kafkaTemplate.send(anyString(), any())).thenThrow(new RuntimeException("Kafka error"));

        assertDoesNotThrow(() -> notificationKafkaProducer.produceAuthorizationNotification(1L, "Test message"));
    }
}