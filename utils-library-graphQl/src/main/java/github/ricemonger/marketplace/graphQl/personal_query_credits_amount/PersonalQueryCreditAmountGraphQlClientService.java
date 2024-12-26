package github.ricemonger.marketplace.graphQl.personal_query_credits_amount;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_credits_amount.DTO.Meta;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCreditAmountMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryCreditAmountGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper;

    public int fetchCreditAmountForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalCreditAmountMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);
        Meta meta = client
                .documentName(GraphQlDocuments.QUERY_CREDITS_AMOUNT_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchCreditAmountVariables())
                .retrieve("game.viewer.meta.secondaryStoreItem.meta")
                .toEntity(Meta.class)
                .block();

        return personalQueryCreditAmountMapper.mapCreditAmount(meta);
    }
}
