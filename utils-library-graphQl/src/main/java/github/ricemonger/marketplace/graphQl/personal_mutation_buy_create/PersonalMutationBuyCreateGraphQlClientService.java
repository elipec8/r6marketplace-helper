package github.ricemonger.marketplace.graphQl.personal_mutation_buy_create;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalMutationBuyCreateGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    public void createBuyOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.documentName(GraphQlDocuments.MUTATION_ORDER_BUY_CREATE_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getCreateBuyOrderVariables(itemId, price))
                .execute().block();
    }
}
