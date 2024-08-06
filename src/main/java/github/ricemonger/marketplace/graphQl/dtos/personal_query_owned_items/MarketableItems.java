package github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketableItems {
    private List<Node> nodes;
    private Integer totalCount;
}
