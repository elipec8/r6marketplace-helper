package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.tradeLimitations.Sell;
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
