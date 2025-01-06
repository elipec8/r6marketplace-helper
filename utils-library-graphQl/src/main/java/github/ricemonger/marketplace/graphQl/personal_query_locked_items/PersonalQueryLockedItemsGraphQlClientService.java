package github.ricemonger.marketplace.graphQl.personal_query_locked_items;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_locked_items.DTO.TradesLimitations;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalLockedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryLockedItemsGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;

    public UserTradesLimitations fetchTradesLimitationsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        TradesLimitations tradesLimitations;

        tradesLimitations = client
                .documentName(GraphQlDocuments.QUERY_LOCKED_ITEMS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchLockedItemsVariables())
                .retrieve("game.viewer.meta.tradesLimitations")
                .toEntity(TradesLimitations.class)
                .block();

        return personalQueryLockedItemsMapper.mapTradesLimitationsForUser(tradesLimitations, authorizationDTO.getProfileId());
    }
}
