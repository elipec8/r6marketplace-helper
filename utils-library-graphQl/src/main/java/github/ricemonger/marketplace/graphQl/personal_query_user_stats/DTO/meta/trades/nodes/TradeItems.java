package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes;


import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeItems {
    private Item item;
}
