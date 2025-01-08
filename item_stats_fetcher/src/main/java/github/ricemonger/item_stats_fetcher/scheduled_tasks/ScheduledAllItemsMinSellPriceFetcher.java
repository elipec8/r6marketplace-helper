package github.ricemonger.item_stats_fetcher.scheduled_tasks;

import github.ricemonger.item_stats_fetcher.services.ItemService;
import github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices.CommonQueryItemMinSellPricesGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsMinSellPriceFetcher {

    private final CommonQueryItemMinSellPricesGraphQlClientService graphQlClientService;

    private final ItemService itemService;

    @Scheduled(fixedRateString = "${app.scheduling.price.fixedRate}", initialDelayString = "${app.scheduling.price.initialDelay}")
    public void fetchAllItemMinSellPrices() {
        Collection<ItemMinSellPrice> items = graphQlClientService.fetchAllItemMinSellPrices();

        itemService.updateAllItemsMinSellPrice(items);
    }
}
