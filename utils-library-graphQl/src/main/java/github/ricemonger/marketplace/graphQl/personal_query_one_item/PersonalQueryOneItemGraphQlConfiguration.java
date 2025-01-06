package github.ricemonger.marketplace.graphQl.personal_query_one_item;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryOneItemGraphQlConfiguration {
    @Bean
    public PersonalQueryOneItemMapper personalQueryOneItemMapper(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new PersonalQueryOneItemMapper(graphQlCommonValuesService);
    }

    @Bean
    public PersonalQueryOneItemGraphQlClientService personalQueryOneItemGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                             GraphQlVariablesService graphQlVariablesService,
                                                                                             PersonalQueryOneItemMapper personalQueryOneItemMapper) {
        return new PersonalQueryOneItemGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryOneItemMapper);
    }
}
