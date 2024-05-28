package github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeItems {
    private Item item;
}
