package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_resolved_transaction_period.TradeLimitations;
import github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_resolved_transaction_period.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.graphsDTOs.config_query_resolved_transaction_period.tradeLimitations.Sell;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigQueryResolvedTransactionPeriodMapper {

    public ConfigResolvedTransactionPeriod mapConfigResolvedTransactionPeriod(TradeLimitations tradeLimitations) {
        ConfigResolvedTransactionPeriod result = new ConfigResolvedTransactionPeriod();

        Buy buy = tradeLimitations.getBuy();

        if(buy != null){
            result.setBuyResolvedTransactionPeriod(buy.getResolvedTransactionPeriodInMinutes());
        }
        else{
            log.error("Buy resolved transaction period not found");
            result.setBuyResolvedTransactionPeriod(0);
        }

        Sell sell = tradeLimitations.getSell();

        if(sell != null){
            result.setSellResolvedTransactionPeriod(sell.getResolvedTransactionPeriodInMinutes());
        }
        else{
            log.error("Sell resolved transaction period not found");
            result.setSellResolvedTransactionPeriod(0);
        }

        return result;
    }
}
