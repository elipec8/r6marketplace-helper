package github.ricemonger.marketplace.graphQl.personal_query_owned_items;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryOwnedItemsGraphQlConfiguration {
    @Bean
    public PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper() {
        return new PersonalQueryOwnedItemsMapper();
    }

    @Bean
    public PersonalQueryOwnedItemsGraphQlClientService personalQueryOwnedItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                   GraphQlVariablesService graphQlVariablesService,
                                                                                                   PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper) {
        return new PersonalQueryOwnedItemsGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryOwnedItemsMapper);
    }
}
