package github.ricemonger.notifications_service.services;

import github.ricemonger.utils.DTOs.personal.SendTextDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationKafkaConsumerTest {
    @Autowired
    private NotificationKafkaConsumer notificationKafkaConsumer;

    @MockBean
    private NotificationService notificationService;

    @Test
    public void consumePrivateNotification_should_handle_to_service() {
        SendTextDTO sendTextDTO = new SendTextDTO(1L, "text");

        notificationKafkaConsumer.consumePrivateNotification(sendTextDTO);

        verify(notificationService).sendPrivateNotificationToUser(1L, "text");
    }

    @Test
    public void consumePublicNotification_should_handle_to_service() {
        SendTextDTO sendTextDTO = new SendTextDTO(1L, "text");

        notificationKafkaConsumer.consumePublicNotification(sendTextDTO);

        verify(notificationService).sendPublicNotificationToUser(1L, "text");
    }

    @Test
    public void consumePublicNotificationToAllUsers_should_handle_to_service() {
        notificationKafkaConsumer.consumePublicNotificationToAllUsers("message");

        verify(notificationService).sendPublicNotificationToAllUsers("message");
    }

    @Test
    public void consumeUbiStatsUpdateNotification_should_handle_to_service() {
        SendTextDTO sendTextDTO = new SendTextDTO(1L, "text");

        notificationKafkaConsumer.consumeUbiStatsUpdateNotification(sendTextDTO);

        verify(notificationService).sendUbiStatsUpdateNotificationToUser(1L, "text");
    }

    @Test
    public void consumeTradeManagerNotification_should_handle_to_service() {
        SendTextDTO sendTextDTO = new SendTextDTO(1L, "text");

        notificationKafkaConsumer.consumeTradeManagerNotification(sendTextDTO);

        verify(notificationService).sendTradeManagerNotificationToUser(1L, "text");
    }

    @Test
    public void consumeAuthorizationNotification_should_handle_to_service() {
        SendTextDTO sendTextDTO = new SendTextDTO(1L, "text");

        notificationKafkaConsumer.consumeAuthorizationNotification(sendTextDTO);

        verify(notificationService).sendAuthorizationNotificationToUser(1L, "text");
    }
}