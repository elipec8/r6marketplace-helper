package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
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


    public Collection<Node> fetchAllOwnedItemStatsForUser(AuthorizationDTO authorizationDTO) {

        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        Map<String, Object> ownedItemsVariables = new HashMap<>(GraphQlClientServiceStatics.getFetchItemsVariables());

        return fetchItemsStatsFromOffset(client, GraphQlClientServiceStatics.FETCH_OWNED_ITEMS_STATS_DOCUMENT_NAME,ownedItemsVariables, 0);
    }

    public Collection<Node> fetchAllItemStats(int expectedItemCount) {

        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();

        Collection<Node> nodes = fetchItemsStatsTillOffset(
                client,
                GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME,
                GraphQlClientServiceStatics.getFetchItemsVariables(),
                expectedItemCount);

        if (nodes.size() > expectedItemCount) {
            nodes.addAll(fetchItemsStatsFromOffset(
                    client,
                    GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME,
                    GraphQlClientServiceStatics.getFetchItemsVariables(),
                    nodes.size()));
        }

        return nodes;
    }

    private Set<Node> fetchItemsStatsTillOffset(GraphQlClient graphQlClient, String documentName, Map<String, Object> documentVariables, int finalOffset) {
        int expectedQueriesCount = (finalOffset / GraphQlClientServiceStatics.MAX_LIMIT) + 1;
        Set<ClientGraphQlResponse> responses = new HashSet<>(expectedQueriesCount);

        MarketableItems marketableItems;

        for (int i = 0; i < expectedQueriesCount; i++) {
            int offset = i * GraphQlClientServiceStatics.MAX_LIMIT;

            responses.add(graphQlClient
                    .documentName(documentName)
                    .variables(documentVariables)
                    .variable("offset", offset)
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

    private List<Node> fetchItemsStatsFromOffset(GraphQlClient graphQlClient, String documentName, Map<String, Object> documentVariables, int offset) {
        List<Node> nodes = new ArrayList<>();

        MarketableItems marketableItems;

        do {

            marketableItems = graphQlClient
                    .documentName(documentName)
                    .variables(documentVariables)
                    .variable("offset", offset)
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
