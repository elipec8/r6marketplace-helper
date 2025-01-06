package github.ricemonger.marketplace.graphQl.config_query_trade;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class ConfigQueryTradeGraphQlConfiguration {
    @Bean
    public ConfigQueryTradeMapper configQueryTradeMapper() {
        return new ConfigQueryTradeMapper();
    }

    @Bean
    public ConfigQueryTradeGraphQlClientService configQueryTradeGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                     GraphQlVariablesService graphQlVariablesService,
                                                                                     ConfigQueryTradeMapper configQueryTradeMapper) {
        return new ConfigQueryTradeGraphQlClientService(graphQlClientFactory, graphQlVariablesService, configQueryTradeMapper);
    }
}
