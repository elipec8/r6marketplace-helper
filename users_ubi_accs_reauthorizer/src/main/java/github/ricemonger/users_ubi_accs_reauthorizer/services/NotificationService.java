package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationKafkaProducer notificationKafkaProducer;

    public void sendAuthorizationNotification(Long userId, String string) {
        notificationKafkaProducer.produceAuthorizationNotification(userId, string);
    }
}
