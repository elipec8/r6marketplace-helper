package github.ricemonger.marketplace.updateFetcher;


import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.services.ItemService;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.MarketableItems;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledItemsStatsFetcher {

    private final GraphQlClientService graphQlClientService;

    private final ItemService itemService;

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5min
    public void fetchItemStats() {

        log.info("Fetching items' stats started");

        long beginTime = System.currentTimeMillis();

        Set<Node> nodes = new HashSet<>();

        int offset = 0;
        MarketableItems marketableItems;

        do {
            marketableItems = graphQlClientService.fetchItemStats(offset);

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while(marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        itemService.saveAll(new ArrayList<>(nodes));

        log.info("Fetched {} items' stats in {} seconds", nodes.size(), (System.currentTimeMillis() - beginTime)/1000);
    }
}
