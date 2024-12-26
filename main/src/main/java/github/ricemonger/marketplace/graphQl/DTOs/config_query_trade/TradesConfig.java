package github.ricemonger.marketplace.graphQl.DTOs.config_query_trade;

import github.ricemonger.marketplace.graphQl.client_services.config_query_trade.DTO.tradesConfig.BuyLimit;
import github.ricemonger.marketplace.graphQl.client_services.config_query_trade.DTO.tradesConfig.SellLimit;
import github.ricemonger.marketplace.graphQl.client_services.config_query_trade.DTO.tradesConfig.TransactionFeesConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradesConfig {
    private Integer saleExpiresAfterMinutes;
    private Integer buySlots;
    private Integer sellSlots;
    private BuyLimit buyLimit;
    private SellLimit sellLimit;
    private Integer resaleLockDurationInMinutes;
    private TransactionFeesConfig[] transactionFeesConfig;
    private Boolean twoFactorAuthenticationRule;
    private Boolean gameOwnershipRule;
}
