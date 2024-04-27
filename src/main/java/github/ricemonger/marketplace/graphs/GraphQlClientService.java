package github.ricemonger.marketplace.graphs;

import github.ricemonger.marketplace.graphs.database.neo4j.enums.UserPlatform;
import github.ricemonger.marketplace.graphs.graphsDTOs.MarketableItems;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.Node;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GraphQlClientService {

    private final GraphQlClient fetchItemStatsClient;

    public GraphQlClientService(UbiServiceConfiguration ubiSessionConfiguration){
        fetchItemStatsClient = buildFetchUpdatesClient(ubiSessionConfiguration);
    }
    private HttpGraphQlClient buildFetchUpdatesClient(UbiServiceConfiguration ubiSessionConfiguration){
        WebClient webClient =
                 defaultWebClientConfigs(ubiSessionConfiguration)
                .baseUrl(ubiSessionConfiguration.getUpdateItemsUrl())
                .build();
        return HttpGraphQlClient.builder(webClient).build();
    }
    private WebClient.Builder defaultWebClientConfigs(UbiServiceConfiguration ubiSessionConfiguration){

        int size = 1024 * 1024 * 10; // 10MB
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", ubiSessionConfiguration.getAuthorization())
                .defaultHeader("Ubi-AppId", ubiSessionConfiguration.getUbiAppId())
                .defaultHeader("Ubi-SessionId", ubiSessionConfiguration.getSessionId())
                .defaultHeader("Ubi-ProfileId", ubiSessionConfiguration.getProfileId())
                .defaultHeader("Ubi-RegionId", ubiSessionConfiguration.getRegionId())
                .defaultHeader("Ubi-LocaleCode", ubiSessionConfiguration.getLocaleCode());
    }

    public List<Node> fetchAllItemStats() {

        List<Node> nodes = fetchExpectedAmountOfAllItemsStats();

        if(nodes.size() > GraphQlClientServiceStatics.ITEMS_COUNT){
            nodes.addAll(fetchAllItemStatsFromOffset(nodes.size()));
        }
        return nodes;
    }

    private List<Node> fetchExpectedAmountOfAllItemsStats(){
        int expectedQueriesCount = (GraphQlClientServiceStatics.ITEMS_COUNT/ GraphQlClientServiceStatics.MAX_LIMIT) + 1;
        List<ClientGraphQlResponse> responses = new ArrayList<>(expectedQueriesCount);

        MarketableItems marketableItems;

        Map<String, Object> variables = GraphQlClientServiceStatics.getDefaultUpdateItemsVariables();

        for(int i = 0; i < expectedQueriesCount; i++){
            int offset = i * GraphQlClientServiceStatics.MAX_LIMIT;
            variables.put("offset", offset);

            responses.add(fetchItemStatsClient
                    .documentName(GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(variables)
                    .execute()
                    .block());
        }

        List<Node> nodes = new ArrayList<>();

        for(ClientGraphQlResponse response : responses){
            marketableItems = response.field("game.marketableItems").toEntity(MarketableItems.class);
            nodes.addAll(marketableItems.getNodes());
        }

        return nodes;
    }

    private List<Node> fetchAllItemStatsFromOffset(int offset) {
        List<Node> nodes = new ArrayList<>();

        MarketableItems marketableItems;
        Map<String, Object> variables = GraphQlClientServiceStatics.getDefaultUpdateItemsVariables();

        do {
            variables.put("offset", offset);

            marketableItems = fetchItemStatsClient
                    .documentName(GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(variables)
                    .retrieve("game.marketableItems")
                    .toEntity(MarketableItems.class)
                    .block();


            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlClientServiceStatics.MAX_LIMIT;
        }
        while(marketableItems.getTotalCount() == GraphQlClientServiceStatics.MAX_LIMIT);

        return nodes;
    }
}
