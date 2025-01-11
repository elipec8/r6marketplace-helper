package github.ricemonger.marketplace.graphQl.config_query_marketplace;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class ConfigQueryMarketplaceGraphQlConfiguration {
    @Bean
    public ConfigQueryMarketplaceMapper configQueryMarketplaceMapper() {
        return new ConfigQueryMarketplaceMapper();
    }

    @Bean
    public ConfigQueryMarketplaceGraphQlClientService configQueryMarketplaceGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                 GraphQlVariablesService graphQlVariablesService,
                                                                                                 ConfigQueryMarketplaceMapper configQueryMarketplaceMapper) {
        return new ConfigQueryMarketplaceGraphQlClientService(graphQlClientFactory, graphQlVariablesService, configQueryMarketplaceMapper);
    }
}
