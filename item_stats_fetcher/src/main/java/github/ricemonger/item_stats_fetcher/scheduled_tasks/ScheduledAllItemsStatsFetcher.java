package github.ricemonger.item_stats_fetcher.scheduled_tasks;


import github.ricemonger.item_stats_fetcher.services.CommonValuesService;
import github.ricemonger.item_stats_fetcher.services.ItemService;
import github.ricemonger.item_stats_fetcher.services.NotificationService;
import github.ricemonger.marketplace.graphQl.common_query_items.CommonQueryItemsGraphQlClientService;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsStatsFetcher {

    private final CommonQueryItemsGraphQlClientService graphQlClientService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    private final NotificationService notificationService;

    @Scheduled(fixedRateString = "${app.scheduling.stats.fixedRate}", initialDelayString = "${app.scheduling.stats.initialDelay}")
    public void fetchAllItemStats() {
        int expectedItemCount = commonValuesService.getExpectedItemCountOrZero();

        Collection<Item> items = graphQlClientService.fetchAllItemStats();

        saveAllItemsAndInsertSales(items);

        if (items.size() < expectedItemCount) {
            log.error("Fetched {} items' stats, expected {}", items.size(), expectedItemCount);
        } else if (items.size() > expectedItemCount) {
            log.info("Fetched {} items' stats, expected {}", items.size(), expectedItemCount);
            onItemsAmountIncrease(expectedItemCount, items.size());
        } else {
            log.info("Fetched {} items' stats", items.size());
        }
    }

    private void saveAllItemsAndInsertSales(Collection<Item> items) {
        itemService.saveAllItemsMainFields(items);
        itemService.insertAllItemsLastSales(items);
    }

    private void onItemsAmountIncrease(int expectedItemCount, int fetchedItemsCount) {
        commonValuesService.setExpectedItemCount(fetchedItemsCount);
        notificationService.notifyAllUsersAboutItemAmountIncrease(expectedItemCount, fetchedItemsCount);
    }
}
