package github.ricemonger.marketplace.graphQl.common_query_items_sale_stats;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.DTO.MarketableItems;
import github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.DTO.marketableItems.Node;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemsSaleStatsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommonQueryItemsSaleStatsGraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final CommonQueryItemsSaleStatsMapper commonQueryItemsSaleStatsMapper;

    public List<GroupedItemDaySalesUbiStats> fetchAllItemSalesUbiStats() throws GraphQlCommonItemsSaleStatsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        MarketableItems marketableItems;
        List<Node> nodes = new ArrayList<>();
        int offset = 0;

        do {
            marketableItems = client
                    .documentName(GraphQlDocuments.QUERY_ITEMS_SALE_STATS_DOCUMENT_NAME)
                    .variables(graphQlVariablesService.getFetchItemsUbiSaleStats(offset))
                    .retrieve("game.marketableItems")
                    .toEntity(MarketableItems.class)
                    .block();

            if (marketableItems == null || marketableItems.getNodes() == null || marketableItems.getTotalCount() == null) {
                throw new GraphQlCommonItemsSaleStatsMappingException("MarketableItems or it's field is null");
            }

            nodes.addAll(marketableItems.getNodes());

            offset += marketableItems.getTotalCount();
        }
        while (marketableItems.getTotalCount() == GraphQlVariablesService.MAX_LIMIT);

        return commonQueryItemsSaleStatsMapper.mapAllItemsSaleStats(nodes);
    }
}
