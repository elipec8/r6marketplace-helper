package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.mappers.*;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemMappingException;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemsSaleStatsMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static github.ricemonger.marketplace.graphQl.GraphQlVariablesService.MAX_LIMIT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class GraphQlClientServiceTest {
    @Autowired
    private GraphQlClientService graphQlClientService;
    @MockBean
    private GraphQlClientFactory graphQlClientFactory;
    @MockBean
    private GraphQlVariablesService graphQlVariablesService;
    @MockBean
    private CommonQueryItemsSaleStatsMapper commonQueryItemsSaleStatsMapper;
    @MockBean
    private PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper;
    @MockBean
    private PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper;
    @MockBean
    private PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper;
    @MockBean
    private PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;
    @MockBean
    private PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper;

    @Test
    public void fetchAllItemSalesUbiStats_should_throw_if_totalCount_or_nodes_is_null() {
        github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems marketableItems =
                new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(List.of(), null);

        Map<String, Object> variables = Map.of(
                "withOwnership", false,
                "spaceId", "ubiSpaceId",
                "limit", MAX_LIMIT,
                "offset", 0,
                "paymentItemId", "paymentItemId",
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", "paymentItemId"));
        ;

        HttpGraphQlClient mockClient = mock(HttpGraphQlClient.class);
        GraphQlClient.RequestSpec mockRequestSpec = mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSpec mockRetrieveSpec = mock(GraphQlClient.RetrieveSpec.class);
        Mono<github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems> mono = Mono.just(marketableItems);

        when(graphQlVariablesService.getFetchItemsUbiSaleStats(anyInt())).thenReturn(variables);

        when(graphQlClientFactory.createMainUserClient()).thenReturn(mockClient);
        when(mockClient.documentName(GraphQlDocuments.QUERY_ITEMS_SALE_STATS_DOCUMENT_NAME)).thenReturn(mockRequestSpec);
        when(mockRequestSpec.variables(variables)).thenReturn(mockRequestSpec);
        when(mockRequestSpec.retrieve("game.marketableItems")).thenReturn(mockRetrieveSpec);
        when(mockRetrieveSpec.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono);

        assertThrows(GraphQlCommonItemsSaleStatsMappingException.class, () -> graphQlClientService.fetchAllItemSalesUbiStats());

        marketableItems = new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(null, 15);
        mono = Mono.just(marketableItems);
        when(mockRetrieveSpec.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono);

        assertThrows(GraphQlCommonItemsSaleStatsMappingException.class, () -> graphQlClientService.fetchAllItemSalesUbiStats());
    }

    @Test
    public void fetchAllItemSalesUbiStats_should_be_executed_once_if_totalCount_smaller_then_limit() {
        List<Node> resultNodes = new ArrayList<>();
        resultNodes.add(new Node(null, null));
        resultNodes.add(new Node(null, null));

        HttpGraphQlClient mockClient = mock(HttpGraphQlClient.class);
        GraphQlClient.RequestSpec mockRequestSpec = mock(GraphQlClient.RequestSpec.class);

        github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems marketableItems1 = new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(List.of(new Node()), MAX_LIMIT - 1);
        Map<String, Object> variables1 = Map.of(
                "withOwnership", false,
                "spaceId", "ubiSpaceId",
                "limit", MAX_LIMIT,
                "offset", 0,
                "paymentItemId", "paymentItemId",
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", "paymentItemId"));

        GraphQlClient.RequestSpec mockRequestSpec1 = mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSpec mockRetrieveSpec1 = mock(GraphQlClient.RetrieveSpec.class);
        Mono<github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems> mono1 = Mono.just(marketableItems1);

        github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems marketableItems2 = new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(List.of(new Node()), MAX_LIMIT);

        Map<String, Object> variables2 = Map.of(
                "withOwnership", false,
                "spaceId", "ubiSpaceId",
                "limit", MAX_LIMIT,
                "offset", MAX_LIMIT,
                "paymentItemId", "paymentItemId",
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", "paymentItemId"));

        GraphQlClient.RequestSpec mockRequestSpec2 = mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSpec mockRetrieveSpec2 = mock(GraphQlClient.RetrieveSpec.class);
        Mono<github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems> mono2 = Mono.just(marketableItems2);

        when(graphQlVariablesService.getFetchItemsUbiSaleStats(0)).thenReturn(variables1);
        when(graphQlVariablesService.getFetchItemsUbiSaleStats(MAX_LIMIT)).thenReturn(variables2);

        when(graphQlClientFactory.createMainUserClient()).thenReturn(mockClient);
        when(mockClient.documentName(GraphQlDocuments.QUERY_ITEMS_SALE_STATS_DOCUMENT_NAME)).thenReturn(mockRequestSpec);

        when(mockRequestSpec.variables(variables1)).thenReturn(mockRequestSpec1);
        when(mockRequestSpec1.retrieve("game.marketableItems")).thenReturn(mockRetrieveSpec1);
        when(mockRetrieveSpec1.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono1);

        when(mockRequestSpec.variables(variables2)).thenReturn(mockRequestSpec2);
        when(mockRequestSpec2.retrieve("game.marketableItems")).thenReturn(mockRetrieveSpec2);
        when(mockRetrieveSpec2.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono2);

        graphQlClientService.fetchAllItemSalesUbiStats();

        verify(commonQueryItemsSaleStatsMapper).mapAllItemsSaleStats(argThat(arg -> arg.contains(new Node()) && arg.size() == 1));
    }

    @Test
    public void fetchAllItemSalesUbiStats_should_be_executed_while_limit_bigger_than_totalCount_and_handle_to_mapper() {
        List<Node> resultNodes = new ArrayList<>();
        resultNodes.add(new Node(null, null));
        resultNodes.add(new Node(null, null));

        HttpGraphQlClient mockClient = mock(HttpGraphQlClient.class);
        GraphQlClient.RequestSpec mockRequestSpec = mock(GraphQlClient.RequestSpec.class);

        github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems marketableItems1 = new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(List.of(new Node()), MAX_LIMIT);
        Map<String, Object> variables1 = Map.of(
                "withOwnership", false,
                "spaceId", "ubiSpaceId",
                "limit", MAX_LIMIT,
                "offset", 0,
                "paymentItemId", "paymentItemId",
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", "paymentItemId"));

        GraphQlClient.RequestSpec mockRequestSpec1 = mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSpec mockRetrieveSpec1 = mock(GraphQlClient.RetrieveSpec.class);
        Mono<github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems> mono1 = Mono.just(marketableItems1);

        github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems marketableItems2 = new github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems(List.of(new Node()), MAX_LIMIT - 1);

        Map<String, Object> variables2 = Map.of(
                "withOwnership", false,
                "spaceId", "ubiSpaceId",
                "limit", MAX_LIMIT,
                "offset", MAX_LIMIT,
                "paymentItemId", "paymentItemId",
                "sortBy", Map.of(
                        "field", "ACTIVE_COUNT",
                        "orderType", "Sell",
                        "direction", "DESC",
                        "paymentItemId", "paymentItemId"));

        GraphQlClient.RequestSpec mockRequestSpec2 = mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSpec mockRetrieveSpec2 = mock(GraphQlClient.RetrieveSpec.class);
        Mono<github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems> mono2 = Mono.just(marketableItems2);

        when(graphQlVariablesService.getFetchItemsUbiSaleStats(0)).thenReturn(variables1);
        when(graphQlVariablesService.getFetchItemsUbiSaleStats(MAX_LIMIT)).thenReturn(variables2);

        when(graphQlClientFactory.createMainUserClient()).thenReturn(mockClient);
        when(mockClient.documentName(GraphQlDocuments.QUERY_ITEMS_SALE_STATS_DOCUMENT_NAME)).thenReturn(mockRequestSpec);

        when(mockRequestSpec.variables(variables1)).thenReturn(mockRequestSpec1);
        when(mockRequestSpec1.retrieve("game.marketableItems")).thenReturn(mockRetrieveSpec1);
        when(mockRetrieveSpec1.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono1);

        when(mockRequestSpec.variables(variables2)).thenReturn(mockRequestSpec2);
        when(mockRequestSpec2.retrieve("game.marketableItems")).thenReturn(mockRetrieveSpec2);
        when(mockRetrieveSpec2.toEntity(github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.MarketableItems.class)).thenReturn(mono2);

        graphQlClientService.fetchAllItemSalesUbiStats();

        verify(commonQueryItemsSaleStatsMapper).mapAllItemsSaleStats(argThat(arg -> arg.containsAll(resultNodes) && arg.size() == resultNodes.size()));
    }

}