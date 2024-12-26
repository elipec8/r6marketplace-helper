package github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_locked_items.tradeLimitations.Sell;
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
