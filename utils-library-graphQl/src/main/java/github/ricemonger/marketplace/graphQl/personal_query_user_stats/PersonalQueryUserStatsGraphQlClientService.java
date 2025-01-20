package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocumentsBuilder;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.Meta;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalLockedItemsMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryUserStatsGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlDocumentsBuilder graphQlDocumentsBuilder;

    private final PersonalQueryUserStatsMapper personalQueryUserStatsMapper;

    public UserTradesLimitations fetchUserStatsForUser(AuthorizationDTO authorizationDTO, Integer ownedItemsExpectedAmount) throws GraphQlPersonalLockedItemsMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        BuiltGraphQlDocuments builtGraphQlDocuments = graphQlDocumentsBuilder.buildPersonalQueryUserStatsDocument(ownedItemsExpectedAmount);

        Meta meta = client
                .document(builtGraphQlDocuments.getDocument())
                .variables(builtGraphQlDocuments.getVariables())
                .retrieve("game.viewer.meta")
                .toEntity(Meta.class)
                .block();

        return personalQueryUserStatsMapper.mapUserStats(meta, builtGraphQlDocuments.getAliasesToFields(), authorizationDTO.getProfileId());
    }
}
