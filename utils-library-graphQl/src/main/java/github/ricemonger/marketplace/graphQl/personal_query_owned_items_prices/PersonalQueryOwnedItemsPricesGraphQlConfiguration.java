package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryOwnedItemsPricesGraphQlConfiguration {
    @Bean
    public PersonalQueryOwnedItemsPricesMapper personalQueryOwnedItemsPricesMapper() {
        return new PersonalQueryOwnedItemsPricesMapper();
    }

    @Bean
    public PersonalQueryOwnedItemsPricesGraphQlClientService personalQueryOwnedItemsPricesGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                               GraphQlVariablesService graphQlVariablesService,
                                                                                                               PersonalQueryOwnedItemsPricesMapper personalQueryOwnedItemsPricesMapper) {
        return new PersonalQueryOwnedItemsPricesGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryOwnedItemsPricesMapper);
    }
}
