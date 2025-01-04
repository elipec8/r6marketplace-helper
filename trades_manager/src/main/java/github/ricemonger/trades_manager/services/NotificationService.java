package github.ricemonger.trades_manager.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationKafkaProducer notificationKafkaProducer;

    public void sendPrivateNotification(Long userId, String string) {
        notificationKafkaProducer.producePrivateNotification(userId, string);
    }
}
