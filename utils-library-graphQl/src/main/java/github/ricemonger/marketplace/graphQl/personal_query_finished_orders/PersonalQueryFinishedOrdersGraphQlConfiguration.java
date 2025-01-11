package github.ricemonger.marketplace.graphQl.personal_query_finished_orders;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryFinishedOrdersGraphQlConfiguration {
    @Bean
    public PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryFinishedOrdersMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryFinishedOrdersGraphQlClientService personalQueryFinishedOrdersGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                           GraphQlVariablesService graphQlVariablesService,
                                                                                                           PersonalQueryFinishedOrdersMapper personalQueryFinishedOrdersMapper) {
        return new PersonalQueryFinishedOrdersGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryFinishedOrdersMapper);
    }
}
