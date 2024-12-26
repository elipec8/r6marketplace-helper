package github.ricemonger.marketplace.graphQl.personal_mutation_buy_create;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AbstractGraphQlConfiguration.class)
public class PersonalMutationBuyCreateGraphQlConfiguration {
    @Bean
    public PersonalMutationBuyCreateGraphQlClientService personalMutationBuyCreateGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                       GraphQlVariablesService graphQlVariablesService) {
        return new PersonalMutationBuyCreateGraphQlClientService(graphQlClientFactory, graphQlVariablesService);
    }
}
