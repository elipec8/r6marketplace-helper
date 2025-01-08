package github.ricemonger.marketplace.graphQl.common_query_items;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class CommonQueryItemsGraphQlConfiguration {

    @Bean
    public CommonQueryItemsMapper commonQueryItemsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new CommonQueryItemsMapper(graphQlCommonValuesService);
    }

    @Bean
    public CommonQueryItemsGraphQlClientService commonQueryItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory, GraphQlVariablesService graphQlVariablesService, CommonQueryItemsMapper commonQueryItemsMapper) {
        return new CommonQueryItemsGraphQlClientService(graphQlClientFactory, graphQlVariablesService, commonQueryItemsMapper);
    }
}
