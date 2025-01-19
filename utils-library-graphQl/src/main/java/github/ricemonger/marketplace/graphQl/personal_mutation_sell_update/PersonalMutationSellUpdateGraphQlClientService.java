package github.ricemonger.marketplace.graphQl.personal_mutation_sell_update;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalMutationSellUpdateGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    public void updateSellOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.document(GraphQlDocuments.MUTATION_ORDER_SELL_UPDATE_DOCUMENT)
                .variables(graphQlVariablesService.getUpdateSellOrderVariables(tradeId, price))
                .execute().block();
    }

    public void nonBlockingUpdateSellOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.document(GraphQlDocuments.MUTATION_ORDER_SELL_UPDATE_DOCUMENT)
                .variables(graphQlVariablesService.getUpdateSellOrderVariables(tradeId, price))
                .execute().subscribe();
    }
}
