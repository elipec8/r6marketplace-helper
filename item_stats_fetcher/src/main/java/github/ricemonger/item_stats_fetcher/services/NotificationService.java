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
        log.info("Public notification about item amount increase from {} to {}", expectedItemCount, fetchedItemsCount);
        try {
            notificationKafkaProducer.producePublicNotificationToAllUsers("Item amount increased from " + expectedItemCount + " to " + fetchedItemsCount + ". Probably new items were added to marketplace.");
        } catch (Exception e) {
            log.warn("Failed to send public notification about item amount increase from {} to {} due to: {}", expectedItemCount, fetchedItemsCount, e.getMessage());
        }
    }
}