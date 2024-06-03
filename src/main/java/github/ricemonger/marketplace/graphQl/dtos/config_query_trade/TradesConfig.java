package github.ricemonger.marketplace.graphQl.dtos.config_query_trade;

import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.BuyLimit;
import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.SellLimit;
import github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig.TransactionFeesConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradesConfig {
    private int saleExpiresAfterMinutes;
    private int buySlots;
    private int sellSlots;
    private BuyLimit buyLimit;
    private SellLimit sellLimit;
    private int resaleLockDurationInMinutes;
    private TransactionFeesConfig[] transactionFeesConfig;
    private boolean twoFactorAuthenticationRule;
    private boolean gameOwnershipRule;
}
