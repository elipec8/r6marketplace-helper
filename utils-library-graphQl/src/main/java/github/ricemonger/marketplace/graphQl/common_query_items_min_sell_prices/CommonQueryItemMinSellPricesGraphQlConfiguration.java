package github.ricemonger.marketplace.graphQl.common_query_items_min_sell_prices;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class CommonQueryItemMinSellPricesGraphQlConfiguration {

    @Bean
    public CommonQueryItemsMinSellPricesMapper commonQueryItemsMinSellPricesMapper() {
        return new CommonQueryItemsMinSellPricesMapper();
    }

    @Bean
    public CommonQueryItemMinSellPricesGraphQlClientService commonQueryItemMinSellPricesGraphQlClientService(GraphQlClientFactory graphQlClientFactory, GraphQlVariablesService graphQlVariablesService, CommonQueryItemsMinSellPricesMapper commonQueryItemsMapper) {
        return new CommonQueryItemMinSellPricesGraphQlClientService(graphQlClientFactory, graphQlVariablesService, commonQueryItemsMapper);
    }
}
