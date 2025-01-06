package github.ricemonger.marketplace.graphQl.common_query_items_sale_stats;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class CommonQueryItemsSaleStatsGraphQlConfiguration {
    @Bean
    public CommonQueryItemsSaleStatsMapper commonQueryItemsSaleStatsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new CommonQueryItemsSaleStatsMapper(graphQlCommonValuesService);
    }

    @Bean
    public CommonQueryItemsSaleStatsGraphQlClientService commonQueryItemsSaleStatsGraphQlService(GraphQlClientFactory graphQlClientFactory,
                                                                                                 GraphQlVariablesService graphQlVariablesService,
                                                                                                 CommonQueryItemsSaleStatsMapper commonQueryItemsSaleStatsMapper) {
        return new CommonQueryItemsSaleStatsGraphQlClientService(graphQlClientFactory, graphQlVariablesService, commonQueryItemsSaleStatsMapper);
    }
}
