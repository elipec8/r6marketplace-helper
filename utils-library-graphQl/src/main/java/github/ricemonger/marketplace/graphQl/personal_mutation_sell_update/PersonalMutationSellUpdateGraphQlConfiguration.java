package github.ricemonger.marketplace.graphQl.personal_mutation_sell_update;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class PersonalMutationSellUpdateGraphQlConfiguration {
    @Bean
    public PersonalMutationSellUpdateGraphQlClientService personalMutationSellUpdateGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                         GraphQlVariablesService graphQlVariablesService) {
        return new PersonalMutationSellUpdateGraphQlClientService(graphQlClientFactory, graphQlVariablesService);
    }
}
