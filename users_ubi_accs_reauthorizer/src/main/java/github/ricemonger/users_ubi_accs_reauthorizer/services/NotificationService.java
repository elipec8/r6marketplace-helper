package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationKafkaProducer notificationKafkaProducer;

    public void sendNotificationToUser(Long userId, String text) {
        notificationKafkaProducer.producePrivateNotification(userId, text);
    }
}
