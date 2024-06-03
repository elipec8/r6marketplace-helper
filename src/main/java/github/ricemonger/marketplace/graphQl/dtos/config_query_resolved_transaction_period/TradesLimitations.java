package github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period;

import github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.dtos.config_query_resolved_transaction_period.tradeLimitations.Sell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradesLimitations {

    private Buy buy;

    private Sell sell;
}
