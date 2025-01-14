package github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders;


import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.Trades;
import github.ricemonger.utils.DTOs.personal.SellTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCurrentOrderMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.List;

@RequiredArgsConstructor
public class PersonalQueryCurrentSellOrdersGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryCurrentSellOrdersMapper personalQueryCurrentSellOrdersMapper;

    public List<SellTrade> fetchCurrentSellOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCurrentOrderMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        Trades trades;

        trades = client
                .document(GraphQlDocuments.QUERY_CURRENT_SELL_ORDERS_DOCUMENT)
                .variables(graphQlVariablesService.getFetchOrdersVariables(0))
                .retrieve("game.viewer.meta.trades")
                .toEntity(Trades.class)
                .block();

        return personalQueryCurrentSellOrdersMapper.mapCurrentOrders(trades);
    }
}
