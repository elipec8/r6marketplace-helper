package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items.PersonalQueryOwnedItemsGraphQlConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({AbstractGraphQlConfiguration.class, PersonalQueryOwnedItemsGraphQlConfiguration.class})
public class PersonalQueryUserStatsGraphQlConfiguration {

    @Bean
    public PersonalQueryUserStatsGraphQlDocumentBuilder personalQueryUserStatsGraphQlDocumentBuilder(GraphQlVariablesService graphQlVariablesService) {
        return new PersonalQueryUserStatsGraphQlDocumentBuilder(graphQlVariablesService);
    }

    @Bean
    public PersonalQueryUserStatsMapper personalQueryUserStatsMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryUserStatsMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryUserStatsGraphQlClientService personalQueryUserStatsGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                 PersonalQueryUserStatsGraphQlDocumentBuilder personalQueryUserStatsGraphQlDocumentBuilder,
                                                                                                 PersonalQueryUserStatsMapper personalQueryUserStatsMapper,
                                                                                                 PersonalQueryOwnedItemsGraphQlClientService personalQueryOwnedItemsGraphQlClientService) {
        return new PersonalQueryUserStatsGraphQlClientService(graphQlClientFactory, personalQueryUserStatsGraphQlDocumentBuilder, personalQueryUserStatsMapper, personalQueryOwnedItemsGraphQlClientService);
    }
}
