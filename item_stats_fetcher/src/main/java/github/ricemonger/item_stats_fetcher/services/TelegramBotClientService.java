package github.ricemonger.item_stats_fetcher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotClientService {

    public void notifyAllUsersAboutItemAmountIncrease(int expectedItemCount, int fetchedItemsCount) {
        log.info("Notifying all users about item amount increase. Expected item count: {}, fetched item count: {}", expectedItemCount, fetchedItemsCount);
        System.out.println("Mocking notification to all users about item amount increase. Expected item count: " + expectedItemCount + ", fetched item count: " + fetchedItemsCount);
    }
}
