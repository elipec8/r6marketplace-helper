package github.ricemonger.utilslibrarykafka;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificationKafkaProducerTest {

    private KafkaTemplate<String, Object> kafkaTemplate = mock(KafkaTemplate.class);

    private NotificationKafkaProducer notificationKafkaProducer = new NotificationKafkaProducer(kafkaTemplate);

    @Test
    void producePrivateNotification_should_handle_to_template() {
        notificationKafkaProducer.producePrivateNotification(1L, "Test message");

        verify(kafkaTemplate).send("private.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void producePublicNotification_should_handle_to_template() {
        notificationKafkaProducer.producePublicNotification(1L, "Test message");

        verify(kafkaTemplate).send("public.notification", new SendTextDTO(1L, "Test message"));
    }

    @Test
    void producePublicNotificationToAllUsers_should_handle_to_template() {
        notificationKafkaProducer.producePublicNotificationToAllUsers("Test message");

        verify(kafkaTemplate).send("public.all.users.notification", "Test message");
    }
}