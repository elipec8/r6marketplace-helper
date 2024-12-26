package github.ricemonger.marketplace.graphQl.config_query_marketplace;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.config_query_marketplace.DTO.Marketplace;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.exceptions.server.GraphQlConfigMarketplaceMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.Collection;

@RequiredArgsConstructor
public class ConfigQueryMarketplaceGraphQlClientService {

    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final ConfigQueryMarketplaceMapper configQueryMarketplaceMapper;

    public Collection<Tag> fetchAllTags() throws GraphQlConfigMarketplaceMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        Marketplace marketplace = client
                .documentName(GraphQlDocuments.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.marketplace")
                .toEntity(Marketplace.class)
                .block();

        return configQueryMarketplaceMapper.mapTags(marketplace);
    }

    public void checkItemTypes() throws GraphQlConfigMarketplaceMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        Marketplace marketplace = client
                .documentName(GraphQlDocuments.QUERY_MARKETPLACE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.marketplace")
                .toEntity(Marketplace.class)
                .block();

        configQueryMarketplaceMapper.checkItemTypes(marketplace);
    }
}
