package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.tradeLimitations.Sell;
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
