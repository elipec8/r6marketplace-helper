package github.ricemonger.item_stats_fetcher.services;

import github.ricemonger.utilslibrarykafka.NotificationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationKafkaProducer notificationKafkaProducer;

    public void notifyAllUsersAboutItemAmountIncrease(int expectedItemCount, int fetchedItemsCount) {
        notificationKafkaProducer.producePublicNotificationToAllUsers("Item amount increased from " + expectedItemCount + " to " + fetchedItemsCount + ". Probably new items were added to marketplace.");
    }
}