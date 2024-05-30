package github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.node.Item;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Item item;
}

