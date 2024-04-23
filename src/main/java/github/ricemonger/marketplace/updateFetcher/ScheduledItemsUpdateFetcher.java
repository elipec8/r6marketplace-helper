package github.ricemonger.marketplace.updateFetcher;


import github.ricemonger.marketplace.updateFetcher.feign.ItemsUpdateFetcherFeignClient;
import github.ricemonger.marketplace.updateFetcher.graphs.game.MarketableItems;
import github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ScheduledItemsUpdateFetcher {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final ItemsUpdateFetcherFeignClient itemsUpdateFetcherFeignClient;

    private final PayloadBuilder payloadBuilder;

    private final GraphQLParses graphQLParses;

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5min
    public void fetchUpdates() {

        List<Node> nodes = new ArrayList<>();

        int offset = 0;
        GraphQlRequestVariables vars = buildFetchUpdateRequestVariables();
        MarketableItems marketableItems;

        do {

            String response = itemsUpdateFetcherFeignClient.fetchUpdate(
                    ubiServiceConfiguration.getAuthorization(),
                    ubiServiceConfiguration.getRegionId(),
                    ubiServiceConfiguration.getLocaleCode(),
                    ubiServiceConfiguration.getUbiAppId(),
                    ubiServiceConfiguration.getSessionId(),
                    ubiServiceConfiguration.getProfileId(),
                    payloadBuilder.buildFetchUpdatePayload(vars));

            marketableItems = graphQLParses.parseFetchUpdateResponse(response);
            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlRequestVariables.MAX_LIMIT;
            vars.setOffset(offset);
        }
        while(marketableItems.getTotalCount() == GraphQlRequestVariables.MAX_LIMIT);

        System.out.println(nodes);
    }

    private GraphQlRequestVariables buildFetchUpdateRequestVariables() {
        GraphQlRequestVariables vars = new GraphQlRequestVariables();
        vars.setMarketplaceOperationName(MarketplaceOperationName.GetMarketableItems);
        vars.setWithOwnership(true);
        vars.setSpaceId(GraphQlRequestVariables.DEFAULT_SPACE_ID);
        vars.setLimit(500);
        vars.setOffset(0);
        vars.setFilterByTypes(Collections.emptyList());
        vars.setFilterByTags(Collections.emptyList());
        vars.setSortByField(SortingField.ACTIVE_COUNT);
        vars.setSortByOrderType(OrderType.Sell);
        return vars;
    }

}
