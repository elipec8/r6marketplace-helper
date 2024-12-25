package github.ricemonger.marketplace.graphQl.client_services.fetchAllItemsStats;

import github.ricemonger.marketplace.graphQl.DTOs.common_query_items.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class FetchAllItemsStatsGraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final CommonQueryItemsMapper commonQueryItemsMapper;

    public Collection<Item> fetchAllItemStats() throws GraphQlCommonItemMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        github.ricemonger.marketplace.graphQl.DTOs.common_query_items.MarketableItems marketableItems;
        List<Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlDocuments.QUERY_ITEMS_STATS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchItemsVariables(offset))
                    .retrieve("game.marketableItems")
                    .toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items.MarketableItems.class)
                    .block();

            if (marketableItems == null || marketableItems.getNodes() == null || marketableItems.getTotalCount() == null) {
                throw new GraphQlCommonItemMappingException("MarketableItems or it's field is null");
            }

            nodes.addAll(marketableItems.getNodes());

            offset += marketableItems.getTotalCount();
        }
        while (marketableItems.getTotalCount() == GraphQlVariablesService.MAX_LIMIT);

        return commonQueryItemsMapper.mapItems(nodes);
    }
}
