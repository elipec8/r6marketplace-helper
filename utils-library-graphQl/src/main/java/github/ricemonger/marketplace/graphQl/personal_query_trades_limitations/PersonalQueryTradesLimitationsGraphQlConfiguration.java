package github.ricemonger.marketplace.graphQl.personal_query_trades_limitations;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryTradesLimitationsGraphQlConfiguration {
    @Bean
    public PersonalQueryTradesLimitationsMapper personalQueryLockedItemsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryTradesLimitationsMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryTradesLimitationsGraphQlClientService personalQueryLockedItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                           GraphQlVariablesService graphQlVariablesService,
                                                                                                           PersonalQueryTradesLimitationsMapper personalQueryTradesLimitationsMapper) {
        return new PersonalQueryTradesLimitationsGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryTradesLimitationsMapper);
    }
}
