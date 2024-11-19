package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.config_query_resolved_transaction_period.TradesLimitations;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_resolved_transaction_period.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_resolved_transaction_period.tradeLimitations.Sell;
import github.ricemonger.utils.DTOs.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.exceptions.server.GraphQlConfigResolvedTransactionPeriodMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigQueryResolvedTransactionPeriodMapper {

    public ConfigResolvedTransactionPeriod mapConfigResolvedTransactionPeriod(TradesLimitations tradesLimitations) throws GraphQlConfigResolvedTransactionPeriodMappingException {

        if (tradesLimitations == null) {
            throw new GraphQlConfigResolvedTransactionPeriodMappingException("Trades limitations is null");
        }

        ConfigResolvedTransactionPeriod result = new ConfigResolvedTransactionPeriod();

        Buy buy = tradesLimitations.getBuy();

        if (buy != null && buy.getResolvedTransactionPeriodInMinutes() != null) {
            result.setBuyResolvedTransactionPeriod(buy.getResolvedTransactionPeriodInMinutes());
        } else {
            throw new GraphQlConfigResolvedTransactionPeriodMappingException("Buy or Buy resolved transaction period is null, buy-" + buy);
        }

        Sell sell = tradesLimitations.getSell();

        if (sell != null && sell.getResolvedTransactionPeriodInMinutes() != null) {
            result.setSellResolvedTransactionPeriod(sell.getResolvedTransactionPeriodInMinutes());
        } else {
            throw new GraphQlConfigResolvedTransactionPeriodMappingException("Sell or Sell resolved transaction period is null, sell-" + sell);
        }

        return result;
    }
}
