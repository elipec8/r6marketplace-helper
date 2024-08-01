package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.TradesConfig;
import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.BuyLimit;
import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.SellLimit;
import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.TransactionFeesConfig;
import github.ricemonger.utils.dtos.ConfigTrades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigQueryTradeMapper {

    public ConfigTrades mapConfigTrades(TradesConfig tradesConfig) {
        ConfigTrades result = new ConfigTrades();

        result.setSaleExpiresAfterMinutes(tradesConfig.getSaleExpiresAfterMinutes());
        result.setBuySlots(tradesConfig.getBuySlots());
        result.setSellSlots(tradesConfig.getSellSlots());
        result.setResaleLockDurationInMinutes(tradesConfig.getResaleLockDurationInMinutes());
        result.setTwoFactorAuthenticationRule(tradesConfig.isTwoFactorAuthenticationRule());
        result.setGameOwnershipRule(tradesConfig.isGameOwnershipRule());

        BuyLimit buyLimit = tradesConfig.getBuyLimit();
        SellLimit sellLimit = tradesConfig.getSellLimit();
        TransactionFeesConfig transactionFeesConfig = tradesConfig.getTransactionFeesConfig() == null || tradesConfig.getTransactionFeesConfig().length == 0 ? null : tradesConfig.getTransactionFeesConfig()[0];

        if (buyLimit != null) {
            result.setBuyLimit(buyLimit.getMaximumCount());
        } else {
            log.error("Buy limit not found");
            result.setBuyLimit(0);
        }

        if (sellLimit != null) {
            result.setSellLimit(sellLimit.getMaximumCount());
        } else {
            log.error("Sell limit not found");
            result.setSellLimit(0);
        }

        if (transactionFeesConfig != null) {
            result.setPaymentItemId(transactionFeesConfig.getPaymentItemId());
            result.setFeePercentage(transactionFeesConfig.getFeePercentage());
        } else {
            log.error("Transaction fees config not found");
            result.setPaymentItemId("");
            result.setFeePercentage(0);
        }
        return result;
    }
}
