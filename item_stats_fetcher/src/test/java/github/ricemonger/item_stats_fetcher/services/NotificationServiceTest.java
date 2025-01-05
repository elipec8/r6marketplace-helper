package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @MockBean
    private NotificationKafkaProducer notificationKafkaProducer;

    @Test
    public void notifyAllUsersAboutItemAmountIncrease_should_produce_notification() {
        notificationService.notifyAllUsersAboutItemAmountIncrease(1, 2);

        verify(notificationKafkaProducer).producePublicNotificationToAllUsers(anyString());
    }
}