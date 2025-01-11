package github.ricemonger.marketplace.graphQl.personal_query_current_orders;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryCurrentOrdersGraphQlConfiguration {
    @Bean
    public PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryCurrentOrdersMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryCurrentOrdersGraphQlClientService personalQueryCurrentOrdersGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                         GraphQlVariablesService graphQlVariablesService,
                                                                                                         PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper) {
        return new PersonalQueryCurrentOrdersGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryCurrentOrdersMapper);
    }
}
