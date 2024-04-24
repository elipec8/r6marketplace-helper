package github.ricemonger.marketplace.updateFetcher;

import github.ricemonger.marketplace.updateFetcher.graphsDTOs.game.MarketableItems;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

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

    public MarketableItems fetchItemStats(Integer offset) {

        Map<String, Object> variables = GraphQlClientServiceStatics.getDefaultUpdateItemsVariables();
        variables.put("offset", offset);

       return fetchItemStatsClient
                .documentName(GraphQlClientServiceStatics.FETCH_ITEMS_STATS_DOCUMENT_NAME)
                .variables(variables)
                .retrieve("game.marketableItems")
                .toEntity(MarketableItems.class)
                .block();
    }
}
