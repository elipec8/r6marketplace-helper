package github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period;

import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.TradesLimitations;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.tradeLimitations.Sell;
import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.exceptions.server.GraphQlConfigResolvedTransactionPeriodMappingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
