package github.ricemonger.marketplace.graphQl.config_query_trade;

import github.ricemonger.marketplace.graphQl.GraphQlClientFactory;
import github.ricemonger.marketplace.graphQl.GraphQlDocuments;
import github.ricemonger.marketplace.graphQl.GraphQlVariablesService;
import github.ricemonger.marketplace.graphQl.config_query_trade.DTO.TradesConfig;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.exceptions.server.GraphQlConfigTradeMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;

@RequiredArgsConstructor
public class ConfigQueryTradeGraphQlClientService {
    private final GraphQlClientFactory graphQlClientFactory;

    private final GraphQlVariablesService graphQlVariablesService;

    private final ConfigQueryTradeMapper configQueryTradeMapper;

    public ConfigTrades fetchConfigTrades() throws GraphQlConfigTradeMappingException {
        HttpGraphQlClient client = graphQlClientFactory.createMainUserClient();
        TradesConfig tradesConfig = client
                .documentName(GraphQlDocuments.QUERY_TRADE_CONFIG_DOCUMENT_NAME)
                .variables(graphQlVariablesService.getFetchConfigVariables())
                .retrieve("game.tradesConfig")
                .toEntity(TradesConfig.class)
                .block();

        return configQueryTradeMapper.mapConfigTrades(tradesConfig);
    }
}
