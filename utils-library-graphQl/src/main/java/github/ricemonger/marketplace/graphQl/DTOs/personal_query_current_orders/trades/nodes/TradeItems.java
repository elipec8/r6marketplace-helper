package github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes;


import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes.tradeItems.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeItems {
    private Item item;
}
