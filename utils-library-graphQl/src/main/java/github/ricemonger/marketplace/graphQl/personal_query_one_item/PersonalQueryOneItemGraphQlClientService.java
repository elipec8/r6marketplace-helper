package github.ricemonger.marketplace.graphQl.personal_query_one_item;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.Game;
import github.ricemonger.utils.DTOs.personal.ItemDetails;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOneItemMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class PersonalQueryOneItemGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final PersonalQueryOneItemMapper personalQueryOneItemMapper;

    public ItemDetails fetchOneItem(AuthorizationDTO authorizationDTO, String itemId) throws GraphQlPersonalOneItemMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        Game game = client
                .documentName(GraphQlDocuments.QUERY_ONE_ITEM_STATS_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchOneItemVariables(itemId))
                .retrieve("game")
                .toEntity(Game.class)
                .block();

        return personalQueryOneItemMapper.mapItem(game);
    }
}
