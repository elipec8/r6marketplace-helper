package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems;

import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices.DTO.marketableItems.node.MarketData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Item item;
    private MarketData marketData;
}

