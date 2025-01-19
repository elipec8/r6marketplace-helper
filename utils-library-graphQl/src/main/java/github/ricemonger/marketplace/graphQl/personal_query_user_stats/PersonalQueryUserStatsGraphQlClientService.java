package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.TradesLimitations;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalLockedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryUserStatsGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryUserStatsMapper personalQueryUserStatsMapper;

    public UserTradesLimitations fetchTradesLimitationsForUser(AuthorizationDTO authorizationDTO) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        TradesLimitations tradesLimitations;

        tradesLimitations = client
                .documentName(GraphQlDocuments.QUERY_USER_STATS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchLockedItemsVariables())
                .retrieve("game.viewer.meta.tradesLimitations")
                .toEntity(TradesLimitations.class)
                .block();

        return personalQueryUserStatsMapper.mapTradesLimitationsForUser(tradesLimitations, authorizationDTO.getProfileId());
    }
}
