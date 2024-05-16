package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.MarketableItems;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;


    public Collection<Node> fetchAllItemStats(int expectedItemCount) {

        HttpGraphQlClient client = graphQlClientFactory.getOrCreateAllItemsStatsFetcherClient();

        Collection<Node> nodes = fetchExpectedAmountOfAllItemsStats(client, expectedItemCount);

        if (nodes.size() > expectedItemCount) {
            nodes.addAll(fetchAllItemStatsFromOffset(client, nodes.size()));
        }

        return nodes;
    }

    private Set<Node> fetchExpectedAmountOfAllItemsStats(GraphQlClient graphQlClient, int expectedItemCount) {
        int expectedQueriesCount = (expectedItemCount / GraphQlClientServiceStatics.MAX_LIMIT) + 1;
        Set<ClientGraphQlResponse> responses = new HashSet<>(expectedQueriesCount);

        MarketableItems marketableItems;

        Map<String, Object> variables = GraphQlClientServiceStatics.getDefaultUpdateItemsVariables();

        for (int i = 0; i < expectedQueriesCount; i++) {
            int offset = i * GraphQlClientServiceStatics.MAX_LIMIT;
            variables.put("offset", offset);

            responses.add(graphQlClient
                    .documentName(GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(variables)
                    .execute()
                    .block());
        }

        Set<Node> nodes = new HashSet<>();

        for (ClientGraphQlResponse response : responses) {
            marketableItems = response.field("game.marketableItems").toEntity(MarketableItems.class);
            nodes.addAll(marketableItems.getNodes());
        }

        return nodes;
    }

    private List<Node> fetchAllItemStatsFromOffset(GraphQlClient graphQlClient, int offset) {
        List<Node> nodes = new ArrayList<>();

        MarketableItems marketableItems;
        Map<String, Object> variables = GraphQlClientServiceStatics.getDefaultUpdateItemsVariables();

        do {
            variables.put("offset", offset);

            marketableItems = graphQlClient
                    .documentName(GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(variables)
                    .retrieve("game.marketableItems")
                    .toEntity(MarketableItems.class)
                    .block();


            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while (marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        return nodes;
    }
}
