package github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryCurrentSellOrdersGraphQlConfiguration {
    @Bean
    public PersonalQueryCurrentSellOrdersMapper personalQueryCurrentOrdersMapper() {
        return new PersonalQueryCurrentSellOrdersMapper();
    }

    @Bean
    public PersonalQueryCurrentSellOrdersGraphQlClientService personalQueryCurrentOrdersGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                             GraphQlVariablesService graphQlVariablesService,
                                                                                                             PersonalQueryCurrentSellOrdersMapper personalQueryCurrentSellOrdersMapper) {
        return new PersonalQueryCurrentSellOrdersGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryCurrentSellOrdersMapper);
    }
}
