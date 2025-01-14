package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.MarketableItems;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.Node;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOwnedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonalQueryOwnedItemsPricesGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryOwnedItemsPricesMapper personalQueryOwnedItemsPricesMapper;

    public List<ItemCurrentPrices> fetchOwnedItemsCurrentPricesForUser(AuthorizationDTO authorizationDTO, int amount) throws GraphQlPersonalOwnedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        MarketableItems marketableItems;
        List<Node> nodes = new ArrayList<>();
        int offset = 0;
        int leftToFetch = amount;

        do {

            int limit = Math.min(leftToFetch, GraphQlVariablesService.MAX_LIMIT);

            if (limit <= 0) {
                break;
            }

            marketableItems = client
                    .document(GraphQlDocuments.QUERY_OWNED_ITEMS_PRICES_DOCUMENT)
                    .variables(graphQlVariablesService.getFetchOwnedItemsPricesVariables(offset, limit))
                    .retrieve("game.viewer.meta.marketableItems")
                    .toEntity(MarketableItems.class)
                    .block();

            if (marketableItems == null || marketableItems.getNodes() == null || marketableItems.getTotalCount() == null) {
                throw new GraphQlPersonalOwnedItemsMappingException("MarketableItems or it's field is null");
            }

            nodes.addAll(marketableItems.getNodes());

            offset += GraphQlVariablesService.MAX_LIMIT;
            leftToFetch -= limit;
        }
        while (marketableItems.getTotalCount() == GraphQlVariablesService.MAX_LIMIT);

        return personalQueryOwnedItemsPricesMapper.mapItems(nodes);
    }
}
