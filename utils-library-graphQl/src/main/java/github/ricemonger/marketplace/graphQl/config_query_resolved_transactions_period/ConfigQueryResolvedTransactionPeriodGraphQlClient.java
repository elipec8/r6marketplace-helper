package github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.TradesLimitations;
import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.exceptions.server.GraphQlConfigResolvedTransactionPeriodMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class ConfigQueryResolvedTransactionPeriodGraphQlClient {

    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper;

    public ConfigResolvedTransactionPeriod fetchConfigResolvedTransactionPeriod() throws GraphQlConfigResolvedTransactionPeriodMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();

        TradesLimitations tradesLimitations = client
                .documentName(GraphQlDocuments.QUERY_RESOLVED_TRANSACTION_PERIOD_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.viewer.meta.tradesLimitations")
                .toEntity(TradesLimitations.class)
                .block();

        return configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
    }
}
