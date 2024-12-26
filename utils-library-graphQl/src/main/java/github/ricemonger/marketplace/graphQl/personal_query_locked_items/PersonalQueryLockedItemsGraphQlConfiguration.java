package github.ricemonger.marketplace.graphQl.personal_query_locked_items;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryLockedItemsGraphQlConfiguration {
    @Bean
    public PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryLockedItemsMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryLockedItemsGraphQlClientService personalQueryLockedItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                     GraphQlVariablesService graphQlVariablesService,
                                                                                                     PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper) {
        return new PersonalQueryLockedItemsGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryLockedItemsMapper);
    }
}
