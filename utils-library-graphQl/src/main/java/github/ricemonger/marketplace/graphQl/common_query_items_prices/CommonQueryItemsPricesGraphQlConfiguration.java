package github.ricemonger.marketplace.graphQl.common_query_items_prices;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class CommonQueryItemsPricesGraphQlConfiguration {
    @Bean
    public CommonQueryItemsPricesMapper commonQueryItemsMapper() {
        return new CommonQueryItemsPricesMapper();
    }

    @Bean
    public CommonQueryItemsPricesGraphQlDocumentBuilder commonQueryItemsPricesGraphQlDocumentBuilder(GraphQlVariablesService graphQlVariablesService) {
        return new CommonQueryItemsPricesGraphQlDocumentBuilder(graphQlVariablesService);
    }

    @Bean
    public CommonQueryItemsPricesGraphQlClientService commonQueryItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                           CommonQueryItemsPricesMapper commonQueryItemsPricesMapper,
                                                                                           CommonQueryItemsPricesGraphQlDocumentBuilder commonQueryItemsPricesGraphQlDocumentBuilder) {
        return new CommonQueryItemsPricesGraphQlClientService(graphQlClientFactory, commonQueryItemsPricesMapper, commonQueryItemsPricesGraphQlDocumentBuilder);
    }
}
