package github.ricemonger.trades_manager.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @MockBean
    private NotificationKafkaProducer notificationKafkaProducer;

    @Test
    public void sendPrivateNotification_should_send_notification() {
        notificationService.sendPrivateNotification(1L, "text");
        verify(notificationKafkaProducer).producePrivateNotification(1L, "text");
    }

    @Test
    public void sendPrivateNotification_should_not_throw_exception_if_kafka_throws() {
        doThrow(new RuntimeException("error")).when(notificationKafkaProducer).producePrivateNotification(1L, "text");

        assertDoesNotThrow(() -> notificationService.sendPrivateNotification(1L, "text"));

        verify(notificationKafkaProducer).producePrivateNotification(1L, "text");

    }
}