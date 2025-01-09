package github.ricemonger.marketplace.graphQl.personal_query_finished_orders;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_finished_orders.DTO.Trades;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalFinishedOrdersMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.List;

@RequiredArgsConstructor
public class PersonalQueryFinishedOrdersGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper;

    public List<UbiTrade> fetchLastFinishedOrdersForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalFinishedOrdersMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        Trades trades;
        int offset = 0;
        int limit = 10;

        trades = client
                .documentName(GraphQlDocuments.QUERY_FINISHED_ORDERS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOrdersVariables(offset, limit))
                .retrieve("game.viewer.meta.trades")
                .toEntity(Trades.class)
                .block();

        if (trades == null || trades.getNodes() == null) {
            throw new GraphQlPersonalFinishedOrdersMappingException("Trades or it's field is null");
        }

        return personalQueryFinishedOrdersMapper.mapFinishedOrders(trades);
    }
}
