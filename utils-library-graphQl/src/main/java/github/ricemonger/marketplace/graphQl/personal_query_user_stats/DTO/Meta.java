package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.MarketableItems;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.SecondaryStoreItem;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.Trades;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.TradesLimitations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private TradesLimitations tradesLimitations;
    private MarketableItems marketableItems;
    private SecondaryStoreItem secondaryStoreItem;
    private Trades trades;
}
