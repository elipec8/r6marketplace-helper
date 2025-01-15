package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlConfiguration {
    @Bean
    public PersonalQueryOwnedItemsPricesAndCurrentSellOrdersMapper personalQueryOwnedItemsPricesMapper() {
        return new PersonalQueryOwnedItemsPricesAndCurrentSellOrdersMapper();
    }

    @Bean
    public PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService personalQueryOwnedItemsPricesGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                                                   GraphQlVariablesService graphQlVariablesService,
                                                                                                                                   PersonalQueryOwnedItemsPricesAndCurrentSellOrdersMapper personalQueryOwnedItemsPricesAndCurrentSellOrdersMapper) {
        return new PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryOwnedItemsPricesAndCurrentSellOrdersMapper);
    }
}
