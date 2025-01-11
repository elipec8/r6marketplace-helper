package github.ricemonger.marketplace.graphQl.personal_query_credits_amount;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalQueryCreditAmountGraphQlConfiguration {
    @Bean
    public PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper() {
        return new PersonalQueryCreditAmountMapper();
    }

    @Bean
    public PersonalQueryCreditAmountGraphQlClientService personalQueryCreditAmountGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                       GraphQlVariablesService graphQlVariablesService,
                                                                                                       PersonalQueryCreditAmountMapper personalQueryCreditAmountMapper) {
        return new PersonalQueryCreditAmountGraphQlClientService(graphQlClientFactory, graphQlVariablesService, personalQueryCreditAmountMapper);
    }
}
