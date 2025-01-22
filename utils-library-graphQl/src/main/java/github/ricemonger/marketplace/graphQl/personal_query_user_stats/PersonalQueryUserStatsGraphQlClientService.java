package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.BuiltGraphQlDocument;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlClientService;
import github.ricemonger.utils.DTOs.personal.UbiUserStats;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalLockedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryUserStatsGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final PersonalQueryUserStatsGraphQlDocumentBuilder personalQueryUserStatsGraphQlDocumentBuilder;

    private final PersonalQueryUserStatsMapper personalQueryUserStatsMapper;

    private final PersonalQueryOwnedItemsGraphQlClientService personalQueryOwnedItemsGraphQlClientService;

    public UbiUserStats fetchUbiUserStatsForUser(AuthorizationDTO authorizationDTO, Integer ownedItemsExpectedAmount) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        int expectedOwnedItemsQueries = Math.max((int) Math.ceil((double) ownedItemsExpectedAmount / GraphQlVariablesService.MAX_LIMIT), 1);

        BuiltGraphQlDocument builtGraphQlDocument = personalQueryUserStatsGraphQlDocumentBuilder.buildPersonalQueryUserStatsDocument(expectedOwnedItemsQueries);

        ClientGraphQlResponse response = client
                .document(builtGraphQlDocument.getDocument())
                .variables(builtGraphQlDocument.getVariables())
                .execute().block();

        UbiUserStats stats = personalQueryUserStatsMapper.mapUserStats(response, builtGraphQlDocument.getAliasesToFields(), authorizationDTO.getProfileId());

        if (stats.getOwnedItemsIds().size() % GraphQlVariablesService.MAX_LIMIT == 0) {
            stats.getOwnedItemsIds().addAll(personalQueryOwnedItemsGraphQlClientService.fetchAllOwnedItemsIdsForUserByOffset(authorizationDTO, stats.getOwnedItemsIds().size()));
        }

        return stats;
    }
}
