package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.DTO.Meta;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOwnedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryOwnedItemsPricesAndCurrentSellOrdersMapper personalQueryOwnedItemsPricesAndCurrentSellOrdersMapper;

    public FastUserUbiStats fetchOwnedItemsCurrentPricesAndSellOrdersForUser(AuthorizationDTO authorizationDTO, int ownedItemsLimit) throws GraphQlPersonalOwnedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        int offset = 0;

        int limit = Math.min(ownedItemsLimit, GraphQlVariablesService.MAX_LIMIT);

        Meta meta = client
                .documentName(GraphQlDocuments.QUERY_OWNED_ITEMS_PRICES_AND_CURRENT_SELL_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOwnedItemsPricesAndCurrentSellOrdersVariables(offset, limit, 0, GraphQlVariablesService.MAX_LIMIT))
                .retrieve("game.viewer.meta")
                .toEntity(Meta.class)
                .block();

        return personalQueryOwnedItemsPricesAndCurrentSellOrdersMapper.mapOwnedItemsPricesAndCurrentSellOrders(meta);
    }
}
