package github.ricemonger.marketplace.graphQl.personal_mutation_cancel;

import github.ricemonger.marketplace.graphQl.AbstractGraphQlConfiguration;
import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AbstractGraphQlConfiguration.class)
public class PersonalMutationCancelGraphQlConfiguration {
    @Bean
    public PersonalMutationCancelGraphQlClientService personalMutationCancelGraphQlClientService(GraphQlClientFactory graphQlClientFactory,
                                                                                                 GraphQlVariablesService graphQlVariablesService) {
        return new PersonalMutationCancelGraphQlClientService(graphQlClientFactory, graphQlVariablesService);
    }
}
