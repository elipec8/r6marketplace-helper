package github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class ConfigQueryResolvedTransactionPeriodGraphQlConfiguration {
    @Bean
    public ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper() {
        return new ConfigQueryResolvedTransactionPeriodMapper();
    }

    @Bean
    public ConfigQueryResolvedTransactionPeriodGraphQlClient configQueryResolvedTransactionPeriodGraphQlClient(GraphQlClientFactory graphQlClientFactory,
                                                                                                               GraphQlVariablesService graphQlVariablesService,
                                                                                                               ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper) {
        return new ConfigQueryResolvedTransactionPeriodGraphQlClient(graphQlClientFactory, graphQlVariablesService, configQueryResolvedTransactionPeriodMapper);
    }
}
