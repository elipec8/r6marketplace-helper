package github.ricemonger.marketplace.graphQl.DTOs.config_query_resolved_transaction_period;

import github.ricemonger.marketplace.graphQl.client_services.config_query_resolved_transactions_period.DTO.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.client_services.config_query_resolved_transactions_period.DTO.tradeLimitations.Sell;
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
