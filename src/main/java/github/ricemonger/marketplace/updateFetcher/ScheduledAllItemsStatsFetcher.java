package github.ricemonger.marketplace.updateFetcher;


import github.ricemonger.marketplace.graphs.GraphQlClientService;
import github.ricemonger.marketplace.graphs.database.neo4j.services.ItemService;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsStatsFetcher {

    private final GraphQlClientService graphQlClientService;

    private final ItemService itemService;

    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 60 * 1000) // every 5 minutes after 1 minute of delay
    public void fetchAllItemStats() {

        List<Node> nodes = graphQlClientService.fetchAllItemStats();

        itemService.saveAll(nodes);

        log.info("Fetched {} items' stats", nodes.size());
    }
}
