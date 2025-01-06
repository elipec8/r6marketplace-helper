package github.ricemonger.marketplace.graphQl.personal_mutation_buy_update;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalMutationBuyUpdateGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    public void updateBuyOrderForUser(AuthorizationDTO authorizationDTO, String tradeId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_BUY_UPDATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getUpdateBuyOrderVariables(tradeId, price))
                .execute().block();
    }
}
