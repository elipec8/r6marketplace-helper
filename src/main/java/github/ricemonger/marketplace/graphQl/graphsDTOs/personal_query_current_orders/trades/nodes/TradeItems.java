package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_current_orders.trades.nodes;

import github.ricemonger.marketplace.graphQl.graphsDTOs.common_query_items.marketableItems.node.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeItems {
    private Item item;
}
