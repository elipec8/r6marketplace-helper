package github.ricemonger.marketplace.graphQl.config_query_trade.DTO.tradesConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFeesConfig {
    private String paymentItemId;
    private Integer feePercentage;
}
