package github.ricemonger.marketplace.scheduled_tasks;


import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.ItemStatsService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.dtos.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsStatsFetcher {
    private final GraphQlClientService graphQlClientService;

    private final ItemStatsService itemStatsService;

    private final CommonValuesService commonValuesService;

    private final TelegramBotService telegramBotService;

    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 60 * 1000) // every 5m after 1m of delay
    public void fetchAllItemStats() {
        int expectedItemCount = 0;
        try {
            expectedItemCount = commonValuesService.getExpectedItemCount();
        } catch (NullPointerException ignore) {
            log.info("Expected item count is not set");
        }

        Collection<Item> items = graphQlClientService.fetchAllItemStats();

        if (items.size() < expectedItemCount) {
            log.error("Fetched {} items' stats, expected {}", items.size(), expectedItemCount);
        } else if (items.size() > expectedItemCount) {
            log.info("Fetched {} items' stats, expected {}", items.size(), expectedItemCount);
            onItemsAmountIncrease(expectedItemCount, items.size());
        } else {
            log.info("Fetched {} items' stats", items.size());
        }

        itemStatsService.saveAllItemsAndSales(items);
        itemStatsService.calculateAndSaveItemsSaleHistoryStats();
    }

    private void onItemsAmountIncrease(int expectedItemCount, int fetchedItemsCount) {
        commonValuesService.setExpectedItemCount(fetchedItemsCount);
        telegramBotService.notifyAllUsersAboutItemAmountIncrease(expectedItemCount, fetchedItemsCount);
    }
}
