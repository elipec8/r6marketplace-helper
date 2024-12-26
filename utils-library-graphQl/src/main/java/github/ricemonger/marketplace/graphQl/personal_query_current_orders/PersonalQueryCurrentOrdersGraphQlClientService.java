package github.ricemonger.marketplace.graphQl.personal_query_current_orders;


import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_current_orders.DTO.Trades;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCurrentOrderMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.List;

@RequiredArgsConstructor
public class PersonalQueryCurrentOrdersGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper;

    public List<UbiTrade> fetchCurrentOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCurrentOrderMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        Trades trades;

        trades = client
                .documentName(GraphQlDocuments.QUERY_CURRENT_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOrdersVariables(0))
                .retrieve("game.viewer.meta.trades")
                .toEntity(Trades.class)
                .block();

        return personalQueryCurrentOrdersMapper.mapCurrentOrders(trades);
    }
}
