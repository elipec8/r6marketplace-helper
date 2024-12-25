package github.ricemonger.item_stats_fetcher.scheduled_tasks;



import github.ricemonger.item_stats_fetcher.services.CommonValuesService;
import github.ricemonger.item_stats_fetcher.services.ItemService;
import github.ricemonger.item_stats_fetcher.services.TelegramBotClientService;
import github.ricemonger.marketplace.graphQl.client_services.fetchAllItemsStats.FetchAllItemsStatsGraphQlClientService;
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

    private final FetchAllItemsStatsGraphQlClientService graphQlClientService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    private final TelegramBotClientService telegramBotService;

    @Scheduled(fixedRate = 3 * 60 * 1000, initialDelay = 60 * 1000) // every 3m after 1m of delay
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

        itemService.saveAllItemsMainFields(items);
        itemService.saveAllItemLastSales(items);
    }

    private void onItemsAmountIncrease(int expectedItemCount, int fetchedItemsCount) {
        commonValuesService.setExpectedItemCount(fetchedItemsCount);
        telegramBotService.notifyAllUsersAboutItemAmountIncrease(expectedItemCount, fetchedItemsCount);
    }
}
