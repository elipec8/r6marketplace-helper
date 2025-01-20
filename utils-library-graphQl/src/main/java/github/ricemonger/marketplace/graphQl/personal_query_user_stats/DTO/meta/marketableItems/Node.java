package github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems;

import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Item item;
}

