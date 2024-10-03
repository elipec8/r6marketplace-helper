package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsUbiStatsFetcher {

    private final GraphQlClientService graphQlClientService;

    private final ItemService itemService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 60 * 3 * 1000) // every 24h after 3m of delay
    public void fetchAllItemUbiStats() {
        List<ItemSaleUbiStats> ubiStats = graphQlClientService.fetchAllItemsUbiStats();

        itemService.saveAllItemsUbiStats(ubiStats);
    }
}
