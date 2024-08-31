package github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.node.PriceHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Item item;
    private List<PriceHistory> priceHistory;
}
