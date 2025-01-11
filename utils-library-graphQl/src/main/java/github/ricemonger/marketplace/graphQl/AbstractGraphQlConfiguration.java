package github.ricemonger.marketplace.graphQl;

import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import org.springframework.context.annotation.Bean;

public class AbstractGraphQlConfiguration {
    @Bean
    public GraphQlUbiServiceConfiguration graphQlUbiServiceConfiguration() {
        return new GraphQlUbiServiceConfiguration();
    }

    @Bean
    public GraphQlCommonValuesService graphQlCommonValuesService(CommonValuesDatabaseService commonValuesDatabaseService, GraphQlUbiServiceConfiguration graphQlUbiServiceConfiguration) {
        return new GraphQlCommonValuesService(commonValuesDatabaseService, graphQlUbiServiceConfiguration);
    }

    @Bean
    public GraphQlClientFactory graphQlClientFactory(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new GraphQlClientFactory(graphQlCommonValuesService);
    }

    @Bean
    public GraphQlVariablesService graphQlVariablesService(GraphQlCommonValuesService graphQlCommonValuesService) {
        return new GraphQlVariablesService(graphQlCommonValuesService);
    }
}
