package github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.node.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Item item;
}

