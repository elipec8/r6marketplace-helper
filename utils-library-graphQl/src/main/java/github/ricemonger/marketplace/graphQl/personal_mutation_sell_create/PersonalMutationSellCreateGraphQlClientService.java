package github.ricemonger.marketplace.graphQl.personal_mutation_sell_create;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalMutationSellCreateGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    public void createSellOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.document(GraphQlDocuments.MUTATION_ORDER_SELL_CREATE_DOCUMENT)
                .variables(graphQlVariablesService.getCreateSellOrderVariables(itemId, price))
                .execute().block();
    }

    public void nonBlockingCreateSellOrderForUser(AuthorizationDTO authorizationDTO, String itemId, int price) {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        client.document(GraphQlDocuments.MUTATION_ORDER_SELL_CREATE_DOCUMENT)
                .variables(graphQlVariablesService.getCreateSellOrderVariables(itemId, price))
                .execute().subscribe();
    }
}
