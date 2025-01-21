package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryUserStatsGraphQlConfiguration {

    @Bean
    public PersonalQueryUserStatsMapper personalQueryLockedItemsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryUserStatsMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryUserStatsGraphQlClientService personalQueryLockedItemsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                   PersonalQueryUserStatsGraphQlDocumentBuilder personalQueryUserStatsGraphQlDocumentBuilder,
                                                                                                   PersonalQueryUserStatsMapper personalQueryUserStatsMapper) {
        return new PersonalQueryUserStatsGraphQlClientService(graphQlClientFactory, personalQueryUserStatsGraphQlDocumentBuilder, personalQueryUserStatsMapper);
    }
}
