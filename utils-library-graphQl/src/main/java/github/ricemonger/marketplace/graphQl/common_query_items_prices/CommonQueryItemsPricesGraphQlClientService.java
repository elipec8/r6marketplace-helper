package github.ricemonger.marketplace.graphQl.common_query_items_prices;

import github.ricemonger.marketplace.graphQl.BuiltGraphQlDocument;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.exceptions.server.GraphQlCommonItemMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.List;

@RequiredArgsConstructor
public class CommonQueryItemsPricesGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final CommonQueryItemsPricesMapper commonQueryItemsPricesMapper;

    private final CommonQueryItemsPricesGraphQlDocumentBuilder commonQueryItemsPricesGraphQlDocumentBuilder;

    public List<ItemCurrentPrices> fetchLimitedItemsStats(int limit) throws GraphQlCommonItemMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();

        BuiltGraphQlDocument builtGraphQlDocument = commonQueryItemsPricesGraphQlDocumentBuilder.buildCommonQueryItemsPricesDocument(limit);

        ClientGraphQlResponse response = client
                .document(builtGraphQlDocument.getDocument())
                .variables(builtGraphQlDocument.getVariables())
                .execute().block();

        return commonQueryItemsPricesMapper.mapLimitedItemsStats(response, builtGraphQlDocument.getAliasesToFields());
    }
}
