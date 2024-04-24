package github.ricemonger.marketplace.updateFetcher;


import github.ricemonger.marketplace.updateFetcher.graphs.game.MarketableItems;
import github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledItemsStatsFetcher {

    private final GraphQlClientService graphQlClientService;

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5min
    public void fetchItemStats() {

        List<Node> nodes = new ArrayList<>();

        int offset = 0;
        MarketableItems marketableItems;

        do {
            marketableItems = graphQlClientService.fetchItemStats(offset).getMarketableItems();

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while(marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        log.info("Fetched {} items", nodes.size());
    }
}
