package github.ricemonger.marketplace.graphQl.personal_mutation_buy_update;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalMutationBuyUpdateGraphQlConfiguration {
    @Bean
    public PersonalMutationBuyUpdateGraphQlClientService personalMutationBuyUpdateGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                       GraphQlVariablesService graphQlVariablesService) {
        return new PersonalMutationBuyUpdateGraphQlClientService(graphQlClientFactory, graphQlVariablesService);
    }
}
