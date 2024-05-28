package github.ricemonger.marketplace.graphQl.dtos.config_query_trade.tradesConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFeesConfig {
    private String paymentItemId;
    private int feePercentage;
}
